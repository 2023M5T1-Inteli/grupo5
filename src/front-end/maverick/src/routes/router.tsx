import React, {useEffect} from 'react';
import { useNavigate } from 'react-router-dom';
import Home from '../pages/Home';
import Form from '../pages/Form';
import Waiting from '../pages/Waiting';
import ViewRoutesMap from '../pages/ViewRoutesMap';
import ViewRoutesMapAnimation from '../pages/ViewAnimateMap';

import { BrowserRouter, Routes, Route } from 'react-router-dom';

function Router() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<Home />} />
                <Route path='/form' element={<Form />} />
                <Route path='/waitingroom' element={<Waiting/>} />
                <Route path='/viewroutesMap' element={<ViewRoutesMap/>} /> 
                <Route path='/viewanimateMap' element={<ViewRoutesMapAnimation/>} />  
            </Routes>
        </BrowserRouter>
    )       

}

export default Router;