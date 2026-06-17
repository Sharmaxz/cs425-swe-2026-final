import { useState, useEffect } from 'react';
import './index.css';

const API_STUDENTS = 'http://localhost:8080/api/v1/students';
const API_COURSES = 'http://localhost:8080/api/v1/courses';

function App() {
  const [students, setStudents] = useState([]);
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [formData, setFormData] = useState({
    matricNumber: '',
    firstName: '',
    lastName: '',
    dateOfAdmission: '',
    courseId: ''
  });

  const loadData = async (honorRoll = false) => {
    setLoading(true);
    setError(null);
    try {
      const url = honorRoll ? `${API_STUDENTS}/honor-roll` : API_STUDENTS;

      const [studentRes, courseRes] = await Promise.all([
        fetch(url),
        fetch(API_COURSES)
      ]);

      if (!studentRes.ok || !courseRes.ok) throw new Error('Failed to fetch data');

      setStudents(await studentRes.json());

      const courseData = await courseRes.json();
      setCourses(courseData);

      if (!formData.courseId && courseData.length > 0) {
        setFormData(prev => ({ ...prev, courseId: courseData[0].courseId }));
      }

    } catch (err) {
      setError('Error connecting to API. Is Spring Boot running?');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleRegister = async (e) => {
    e.preventDefault();
    const { courseId, ...payload } = formData;
    try {
      const res = await fetch(`${API_STUDENTS}/register?courseId=${courseId}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      });
      if (res.ok) {
        alert('✅ Student registered successfully!');
        setFormData(prev => ({ ...prev, matricNumber: '', firstName: '', lastName: '', dateOfAdmission: '' }));
        loadData(false);
      } else {
        const errData = await res.json();
        alert(`❌ Error: ${errData.message || 'Validation Failed'}`);
      }
    } catch (err) {
      alert('❌ Error connecting to server!');
    }
  };

  return (
    <div style={{ padding: '2rem', width: '100%', maxWidth: '1200px' }}>
      <h1 style={{ textAlign: 'center', fontSize: '2.5rem', marginBottom: '2rem', background: 'linear-gradient(to right, #818CF8, #C7D2FE)', WebkitBackgroundClip: 'text', WebkitTextFillColor: 'transparent' }}>
        🎓 Prince University Admin
      </h1>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))', gap: '2rem' }}>

        <div style={cardStyle}>
          <h2 style={titleStyle}>Register New Student</h2>
          <form onSubmit={handleRegister} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
            <input style={inputStyle} type="text" placeholder="Matric Number (ex: E999)" required value={formData.matricNumber} onChange={e => setFormData({ ...formData, matricNumber: e.target.value })} />
            <input style={inputStyle} type="text" placeholder="First Name" required value={formData.firstName} onChange={e => setFormData({ ...formData, firstName: e.target.value })} />
            <input style={inputStyle} type="text" placeholder="Last Name" required value={formData.lastName} onChange={e => setFormData({ ...formData, lastName: e.target.value })} />

            <input
              style={{ ...inputStyle, cursor: 'pointer' }}
              type="date"
              required
              value={formData.dateOfAdmission}
              onChange={e => setFormData({ ...formData, dateOfAdmission: e.target.value })}
              onClick={(e) => {
                try { e.target.showPicker(); } catch (err) { }
              }}
            />

            <select
              style={{ ...inputStyle, appearance: 'none', cursor: 'pointer' }}
              required
              value={formData.courseId}
              onChange={e => setFormData({ ...formData, courseId: e.target.value })}
            >
              {courses.length === 0 && <option value="">Loading courses...</option>}
              {courses.map(c => (
                <option key={c.courseId} value={c.courseId} style={{ color: '#0F172A' }}>
                  {c.courseCode} - {c.courseName} (Credits: {c.creditScore})
                </option>
              ))}
            </select>

            <button type="submit" style={btnStyle}>Register & Enroll</button>
          </form>
        </div>

        {/* Roster Dashboard */}
        <div style={cardStyle}>
          <h2 style={titleStyle}>
            Student Roster
            <div style={{ display: 'flex', gap: '0.5rem' }}>
              <button style={{ ...btnStyle, padding: '0.5rem 1rem', fontSize: '0.8rem' }} onClick={() => loadData(false)}>All</button>
              <button style={{ ...btnStyle, padding: '0.5rem 1rem', fontSize: '0.8rem', background: 'linear-gradient(135deg, #059669, #34D399)' }} onClick={() => loadData(true)}>Honor Roll</button>
            </div>
          </h2>

          <ul style={{ listStyle: 'none', maxHeight: '400px', overflowY: 'auto' }}>
            {loading && <li style={emptyStyle}>Loading...</li>}
            {error && <li style={{ ...emptyStyle, color: '#EF4444' }}>{error}</li>}
            {!loading && !error && students.length === 0 && <li style={emptyStyle}>No students found.</li>}
            {!loading && !error && students.map(s => (
              <li key={s.studentId} style={listItemStyle}>
                <div>
                  <strong>{s.lastName}, {s.firstName}</strong> <span style={{ color: '#94A3B8', fontSize: '0.9rem' }}>({s.matricNumber})</span>
                  <div style={{ fontSize: '0.85rem', color: '#818CF8', marginTop: '4px' }}>Courses: {s.courses?.map(c => c.courseCode).join(', ') || 'None'}</div>
                </div>
                <span style={badgeStyle}>⭐ {s.totalCreditScore}</span>
              </li>
            ))}
          </ul>
        </div>

      </div>
    </div>
  );
}

const cardStyle = {
  background: 'var(--card-bg)',
  backdropFilter: 'blur(12px)',
  border: '1px solid rgba(255, 255, 255, 0.1)',
  borderRadius: '1rem',
  padding: '2rem',
  boxShadow: '0 25px 50px -12px rgba(0, 0, 0, 0.5)',
};

const titleStyle = {
  marginBottom: '1.5rem',
  fontSize: '1.5rem',
  color: '#C7D2FE',
  display: 'flex',
  justifyContent: 'space-between',
  alignItems: 'center'
};

const inputStyle = {
  width: '100%',
  padding: '0.75rem',
  background: 'rgba(15, 23, 42, 0.6)',
  border: '1px solid rgba(255, 255, 255, 0.1)',
  borderRadius: '0.5rem',
  color: 'white',
  outline: 'none',
  transition: 'border-color 0.2s'
};

const btnStyle = {
  background: 'linear-gradient(135deg, var(--primary), var(--secondary))',
  color: 'white',
  border: 'none',
  padding: '0.75rem 1.5rem',
  borderRadius: '0.5rem',
  fontWeight: '600',
  cursor: 'pointer',
  transition: 'transform 0.2s, opacity 0.2s',
  boxShadow: '0 4px 6px -1px rgba(79, 70, 229, 0.4)'
};

const listItemStyle = {
  background: 'rgba(255, 255, 255, 0.05)',
  marginBottom: '0.5rem',
  padding: '1rem',
  borderRadius: '0.5rem',
  borderLeft: '4px solid var(--primary)',
  display: 'flex',
  justifyContent: 'space-between',
  alignItems: 'center'
};

const badgeStyle = {
  background: 'rgba(16, 185, 129, 0.2)',
  color: '#34D399',
  padding: '0.25rem 0.75rem',
  borderRadius: '999px',
  fontSize: '0.875rem',
  fontWeight: 'bold'
};

const emptyStyle = {
  textAlign: 'center',
  color: '#94A3B8',
  padding: '2rem'
};

export default App;
