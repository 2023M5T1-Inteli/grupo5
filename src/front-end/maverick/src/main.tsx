import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import ViewRoutes from './screens/routes-screen'
import './index.css'

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <ViewRoutes />
  </React.StrictMode>,
)
