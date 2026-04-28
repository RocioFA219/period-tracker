import { useEffect, useState } from 'react'
import { getCyclesByUserId, registerPeriod } from './services/cycleService'
import { Droplets, Flower2, Calendar as CalendarIcon, LogOut } from 'lucide-react'
import Calendar from 'react-calendar'
import RegisterForm from './components/RegisterForm'
import 'react-calendar/dist/Calendar.css'
import './App.css'

function App() {
  const [userId, setUserId] = useState(null);
  const [userName, setUserName] = useState('');
  const [cycles, setCycles] = useState([]);
  const [activeCycle, setActiveCycle] = useState(null);
  const [date, setDate] = useState(new Date().toISOString().split('T')[0]);

  const loadData = (id) => {
    getCyclesByUserId(id)
      .then(res => {
        const allCycles = res.data;
        setCycles(allCycles);
        const active = allCycles.find(c => c.status === 'ACTIVE');
        setActiveCycle(active);
        if (allCycles.length > 0) setUserName(allCycles[0].userName);
      })
      .catch(err => console.error("Error al cargar:", err));
  };

  useEffect(() => {
    if (userId) loadData(userId);
  }, [userId]);

  const handleLoginSuccess = (id) => {
    setUserId(id);
  };

  const handleRegisterPeriod = () => {
    registerPeriod(userId, date)
      .then(() => {
        alert("¡Periodo registrado!");
        loadData(userId);
      })
      .catch(err => alert(err.response?.data?.message || "Error al registrar"));
  };

  const getDayOfCycle = (startDate) => {
    const start = new Date(startDate);
    const today = new Date();
    today.setHours(0,0,0,0);
    const diffTime = Math.abs(today - start);
    return Math.ceil(diffTime / (1000 * 60 * 60 * 24));
  };

  const tileClassName = ({ date, view }) => {
    if (view === 'month' && activeCycle) {
      const currentTileDate = new Date(date.setHours(0,0,0,0));
      const start = new Date(activeCycle.startDate);
      start.setHours(0,0,0,0);
      const end = new Date(start);
      end.setDate(start.getDate() + 4);
      if (currentTileDate >= start && currentTileDate <= end) return 'highlight-period';
    }
  };

  if (!userId) {
    return (
      <div style={{ backgroundColor: '#fff5f7', minHeight: '100vh', padding: '40px 20px', display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <header style={{ textAlign: 'center', marginBottom: '30px' }}>
          <Flower2 color="#d63384" size={60} />
          <h1 style={{ color: '#d63384', marginTop: '10px' }}>CycleTracker</h1>
        </header>
        <div style={{ width: '100%', maxWidth: '400px' }}>
          <RegisterForm onRegistrationSuccess={handleLoginSuccess} />
        </div>
      </div>
    );
  }

  return (
    <div style={{ backgroundColor: '#fff5f7', minHeight: '100vh', padding: '20px', fontFamily: 'Segoe UI, sans-serif' }}>
      <div style={{ maxWidth: '500px', margin: '0 auto' }}>
        <header style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '25px' }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
            <Flower2 color="#d63384" size={28} />
            <h2 style={{ margin: 0, color: '#d63384' }}>Dashboard</h2>
          </div>
          <button onClick={() => setUserId(null)} style={{ border: 'none', background: 'none', cursor: 'pointer', color: '#6c757d', display: 'flex', alignItems: 'center', gap: '5px' }}>
            <LogOut size={18} /> Salir
          </button>
        </header>

        <div style={{ background: 'linear-gradient(135deg, #d63384 0%, #ff85a2 100%)', color: 'white', padding: '30px', borderRadius: '30px', textAlign: 'center', boxShadow: '0 15px 35px rgba(214, 51, 132, 0.25)', marginBottom: '30px' }}>
          <h3 style={{ margin: 0, opacity: 0.9, fontWeight: 'normal' }}>Hola, {userName || 'Usuaria'}</h3>
          {activeCycle ? (
            <div style={{ marginTop: '20px' }}>
              <div style={{ fontSize: '3.5rem', fontWeight: 'bold' }}>Día {getDayOfCycle(activeCycle.startDate)}</div>
              <div style={{ fontSize: '1.2rem', marginTop: '10px' }}>de tu ciclo</div>
              <div style={{ marginTop: '25px', backgroundColor: 'rgba(255,255,255,0.2)', padding: '10px', borderRadius: '15px' }}>
                Próxima predicción: {new Date(activeCycle.expectedNextDate).toLocaleDateString()}
              </div>
            </div>
          ) : (
            <p style={{ marginTop: '20px' }}>Inicia tu ciclo para ver las predicciones</p>
          )}
        </div>

        <div className="calendar-container" style={{ backgroundColor: 'white', padding: '20px', borderRadius: '25px', boxShadow: '0 4px 15px rgba(0,0,0,0.05)', marginBottom: '30px' }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '8px', marginBottom: '15px' }}><CalendarIcon size={20} color="#495057" /><h4 style={{ margin: 0, color: '#495057' }}>Calendario</h4></div>
          <Calendar locale="es-ES" tileClassName={tileClassName} value={new Date()} />
        </div>

        {!activeCycle && (
          <div style={{ backgroundColor: 'white', padding: '20px', borderRadius: '25px', boxShadow: '0 4px 15px rgba(0,0,0,0.05)' }}>
            <h4 style={{ marginTop: 0, color: '#495057' }}>¿Ha empezado hoy?</h4>
            <div style={{ display: 'flex', gap: '10px' }}>
              <input type="date" value={date} onChange={(e) => setDate(e.target.value)} style={{ flex: 1, padding: '12px', borderRadius: '12px', border: '1px solid #eee' }} />
              <button onClick={handleRegisterPeriod} style={{ backgroundColor: '#d63384', color: 'white', border: 'none', padding: '12px 20px', borderRadius: '12px', fontWeight: 'bold', cursor: 'pointer' }}>Registrar</button>
            </div>
          </div>
        )}
      </div>
    </div>
  )
}

export default App