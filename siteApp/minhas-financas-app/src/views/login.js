import React from 'react'
import Card from '../components/Card';
import FormGroup from '../components/form-group';
import { withRouter } from 'react-router-dom';
import UsuarioService from '../app/Service/usuarioService';
import LocalStorage from '../app/Service/localStorage';
import { mensagemErro } from '../components/toastr'
import { AuthContext } from '../main/provedorAutenticacao'

class Login extends React.Component{

    state = {
        email: '',
        senha: '',
    }

    constructor(){
        super();
        this.service = new UsuarioService();
    }

    entrar = async () => {
      this.service.autenticar({
            email: this.state.email,
            senha: this.state.senha
        }).then( response => {
            LocalStorage.adcionarItem('_usuario_logado', response.data)
            this.context.iniciarSessao(response.data)
            this.props.history.push('/home')
        }).catch( erro => {
                mensagemErro(erro.response.data)
        })
    }

    prepararCadastro = () => {
        this.props.history.push('/cadastro-usuarios')
    }

    render(){
        return(
            <>
            
                <div className="row">
                    <div className="col-md-6" style={ {position: 'relative', left: '300px'} }>
                        <div className="bs-docs-section">
                            <Card title = "login">
                                <div className="row">
                                    <div className="col-lg-12">
                                        <div className="bs-component">
                                            <fieldset>
                                                <FormGroup label = "Email: " htmlFor = "exampleInputEmail">
                                                <input type="email" value={this.state.email}
                                                onChange={e => this.setState({email: e.target.value})}
                                                 class="form-control" id="exampleInputEmail1" 
                                                 aria-describedby="emailHelp" 
                                                placeholder="Digite o Email"/>
                                                </FormGroup>

                                                <FormGroup label = "senha" htmlFor = "exampleInputPassword1">
                                                <input type="password"  value={this.state.senha} 
                                                onChange={e => this.setState({senha: e.target.value})}    class="form-control" 
                                                id="exampleInputPassword1" placeholder="Password"/>
                                                </FormGroup>

                                                <button onClick={this.entrar} 
                                                className="btn btn-success">
                                                   <i className="pi pi-sign-in"></i> Entrar
                                                </button>
                                                <button onClick={this.prepararCadastro} 
                                                className = "btn btn-danger">
                                                  <i className="pi pi-plus"></i>  Cadastrar
                                                </button>
                                            </fieldset>
                                        </div>

                                    </div>
                                </div>
                            </Card>
                        </div>
                    </div>
                </div>
                </>
        );
    }
}

Login.contextType = AuthContext

export default withRouter (Login);