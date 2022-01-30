import React from "react";

import {Route, Switch, HashRouter, Redirect } from 'react-router-dom'
import CadastroUsuario from "../views/cadastroUsuario";
import Login from "../views/login";
import Home from "../views/home";
import ConsultaLancamentos from "../views/lancamentos/consulta-lancamentos";
import CadastroLancamentos from "../views/lancamentos/cadastro-lancamentos";
import {AuthConsumer} from "../main/provedorAutenticacao"



    function RotaAutenticada({component: Component,  isUsuarioAutenticado, ...props}){
        return(
            <Route {...props} render={(componentsProps) =>{
                if(isUsuarioAutenticado){
                 return(
                    <Component {...componentsProps} />
                 
                    )
                }else{
                    return(
                        <Redirect to= {{pathname: "/login", state: {from: componentsProps.location}}} />
                    )
                }
            }} />
        )
    }

function Rotas(props){
    return(
        <HashRouter>
            <Switch>
               <Route path="/login" component={Login}/>
               <Route path="/cadastro-usuarios" component = {CadastroUsuario}/> 


               <RotaAutenticada isUsuarioAutenticado={props.isUsuarioAutenticado} path="/home" component={Home}  />
               <RotaAutenticada isUsuarioAutenticado={props.isUsuarioAutenticado} path="/consulta-lancamentos" component={ConsultaLancamentos}/>
               <RotaAutenticada isUsuarioAutenticado={props.isUsuarioAutenticado} path="/cadastro-lancamento/:id?" component = {CadastroLancamentos}/>
            </Switch>
        </HashRouter>


    )
}

export default () => (
    <AuthConsumer>
        {(context) => (<Rotas isUsuarioAutenticado =  {context.isAutenticado} />)}
    </AuthConsumer>
);