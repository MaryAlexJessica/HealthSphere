import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import AppWrapper from './AppWrapper.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <AppWrapper />   {/* ✅ Always wrap App with UserProvider */}
  </StrictMode>,
)
