import React, {useEffect} from 'react';
import { useNavigate } from 'react-router-dom';
import Home from '../pages/Home';
import Form from '../pages/Form';
import Waiting from '../pages/Waiting';
import ViewRoutesGraph from '../pages/ViewRoutesGraph';
import ViewRoutesMap from '../pages/ViewRoutesMap';

import { BrowserRouter, Routes, Route } from 'react-router-dom';

function Router() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<Home />} />
                <Route path='/form' element={<Form />} />
                <Route path='/waitingroom' element={<Waiting/>} />
                <Route path='/viewroutes' element={<ViewRoutesGraph/>} />
                <Route path='/viewroutesMap' element={<ViewRoutesMap/>} />   
            </Routes>
        </BrowserRouter>
    )       

}

export default Router;