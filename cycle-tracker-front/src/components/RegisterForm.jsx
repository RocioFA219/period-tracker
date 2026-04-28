import { useState } from 'react';
// IMPORTANTE: Añadida la importación que faltaba
import { registerUser, getUserByEmail, loginWithPassword } from '../services/userService';
// Renombramos User a UserIcon para evitar el error "Illegal Constructor"
import { UserPlus, LogIn, Clock, Mail, User as UserIcon, Lock } from 'lucide-react';

const RegisterForm = ({ onRegistrationSuccess }) => {
    const [isLogin, setIsLogin] = useState(true);
    const [formData, setFormData] = useState({
        username: '',
        email: '',
        password: '',
        averageCycleLength: 28
    });

    const handleAction = async (e) => {
        e.preventDefault();
        try {
            if (isLogin) {
                const emailRes = await getUserByEmail(formData.email);
                if (emailRes.data) {
                    const authRes = await loginWithPassword(formData.password);
                    if (authRes.data) {
                        onRegistrationSuccess(authRes.data.id);
                    }
                }
            } else {
                const res = await registerUser(formData);
                alert("¡Usuaria registrada!");
                onRegistrationSuccess(res.data.id);
            }
        } catch (err) {
            alert("Error en la operación. Revisa tus datos.");
            console.error(err);
        }
    };

    return (
      <div style={{ backgroundColor: 'white', padding: '30px', borderRadius: '25px', boxShadow: '0 10px 25px rgba(0,0,0,0.05)' }}>
        <div style={{ display: 'flex', marginBottom: '25px', borderBottom: '1px solid #eee' }}>
            <button onClick={() => setIsLogin(true)} style={{ ...tabStyle, borderBottom: isLogin ? '2px solid #d63384' : 'none', color: isLogin ? '#d63384' : '#6c757d' }}>Entrar</button>
            <button onClick={() => setIsLogin(false)} style={{ ...tabStyle, borderBottom: !isLogin ? '2px solid #d63384' : 'none', color: !isLogin ? '#d63384' : '#6c757d' }}>Nueva Cuenta</button>
        </div>

        <form onSubmit={handleAction} style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
            {!isLogin && (
                <div style={inputContainer}>
                    <UserIcon size={18} color="#adb5bd" />
                    <input placeholder="Nombre de usuaria" style={inputStyle} required onChange={e => setFormData({...formData, username: e.target.value})} />
                </div>
            )}
            
            <div style={inputContainer}>
                <Mail size={18} color="#adb5bd" />
                <input type="email" placeholder="Email" style={inputStyle} required onChange={e => setFormData({...formData, email: e.target.value})} />
            </div>

            <div style={inputContainer}>
                <Lock size={18} color="#adb5bd" />
                <input type="password" placeholder="Contraseña" style={inputStyle} required onChange={e => setFormData({...formData, password: e.target.value})} />
            </div>

            {!isLogin && (
                <div style={{ display: 'flex', alignItems: 'center', gap: '10px', padding: '0 10px' }}>
                    <Clock size={18} color="#6c757d" />
                    <label style={{ fontSize: '0.85rem', color: '#6c757d' }}>Ciclo medio:</label>
                    <input type="number" value={formData.averageCycleLength} style={{ ...inputStyle, width: '60px', border: '1px solid #eee' }} onChange={e => setFormData({...formData, averageCycleLength: parseInt(e.target.value)})} />
                </div>
            )}

            <button type="submit" style={buttonStyle}>
                {isLogin ? <><LogIn size={18} /> Iniciar Sesión</> : <><UserPlus size={18} /> Registrarme</>}
            </button>
        </form>
      </div>
    );
};

// Estilos (se mantienen igual)
const tabStyle = { flex: 1, padding: '10px', border: 'none', background: 'none', cursor: 'pointer', fontWeight: 'bold' };
const inputContainer = { display: 'flex', alignItems: 'center', gap: '10px', border: '1px solid #eee', padding: '10px 15px', borderRadius: '12px' };
const inputStyle = { border: 'none', outline: 'none', width: '100%', fontSize: '1rem' };
const buttonStyle = { backgroundColor: '#d63384', color: 'white', border: 'none', padding: '15px', borderRadius: '12px', fontWeight: 'bold', cursor: 'pointer', marginTop: '10px', display: 'flex', alignItems: 'center', justifyContent: 'center', gap: '10px' };

export default RegisterForm;