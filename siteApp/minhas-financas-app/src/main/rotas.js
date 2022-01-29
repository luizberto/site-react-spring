import React from "react";

import {Route, Switch, HashRouter } from 'react-router-dom'
import CadastroUsuario from "../views/cadastroUsuario";
import Login from "../views/login";
import Home from "../views/home";
import ConsultaLancamentos from "../views/lancamentos/consulta-lancamentos";

function Rotas(){
    return(
        <HashRouter>
            <Switch>
               <Route path="/login" component={Login}/>
               <Route path="/cadastro-usuarios" component = {CadastroUsuario}/> 
               <Route path="/home" component={Home}  />
               <Route path="/consulta-lancamentos" component={ConsultaLancamentos}/>
            </Switch>
        </HashRouter>


    )
}

export default Rotas;