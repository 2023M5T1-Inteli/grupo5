import React, {useEffect} from 'react';
import { useNavigate } from 'react-router-dom';
import Home from '../pages/Home';
import Form from '../components/Form';
import Waiting from '../pages/Waiting';
import ViewRoutesMap from '../pages/ViewRoutesMap';
import ViewRoutesMapAnimation from '../pages/ViewAnimateMap';

import { BrowserRouter, Routes, Route } from 'react-router-dom';
import ViewExclusionZone from '../pages/ViewExclusionZone';

function Router() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<Home />} />
                <Route path='/waitingroom' element={<Waiting/>} />
                <Route path='/viewroutesMap' element={<ViewRoutesMap/>} /> 
                <Route path='/viewanimateMap' element={<ViewRoutesMapAnimation/>} />  
                <Route path='/exclusionZone' element={<ViewExclusionZone/>}/>
            </Routes>
        </BrowserRouter>
    )       

}

export default Router;