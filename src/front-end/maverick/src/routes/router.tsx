import React from 'react';

import Home from '../pages/Home';
import Form from '../pages/Form';
import Waiting from '../pages/Waiting'; 
import { BrowserRouter, Routes, Route } from 'react-router-dom';

function Router() {

    return (
        <React.StrictMode>
            <BrowserRouter>
                <Routes>
                    <Route path='/' element={<Home />} />
                    <Route path='/form' element={<Form />} />
                    <Route path='/waitingroom' element={<Waiting/>} />   
                </Routes>
            </BrowserRouter>
        </React.StrictMode>
    )       

}

export default Router;