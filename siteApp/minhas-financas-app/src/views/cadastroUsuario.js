import React from "react";
import Card from "../components/Card";
import FormGroup from "../components/form-group";

class CadastroUsuario extends React.Component{
    
    state = {
        nome: '',
        email: '',
        senha: '',
        senhaRepeticao: ''
    }

    cadastrar = () => {
        console.log(this.state);
    }

    render(){
        return(
            <div className="container">
                <Card title = "Cadastro de Usuario">
                    <div className="row">
                        <div className="col-lg-12">
                            <div className="bs-component">
                                <FormGroup label = "Nome: " htmlFor = "inputNome">
                                    <input type="text"
                                    id="inputNome"
                                    name="nome"
                                    onChange={e => this.setState({nome: e.target.value})}
                                    />
                                </FormGroup>

                                <FormGroup label = "Email: " htmlFor = "inputEmail">
                                    <input type="email"
                                    id="inputEmail"
                                    className="form-control"
                                    name="email"
                                    onChange={e => this.setState({email: e.target.value})}
                                    />
                                </FormGroup>

                                <FormGroup label = "Senha: " htmlFor = "inputSenha">
                                    <input type="password"
                                    id="inputSenha"
                                    className="form-control"
                                    name="senha"
                                    onChange={e => this.setState({senha: e.target.value})}
                                    />
                                </FormGroup>

                                <FormGroup label = "Repetir Senha: " htmlFor = "inputRepitaSenha">
                                    <input type="password"
                                    id="inputSenha"
                                    className="form-control"
                                    name="senha"
                                    onChange={e => this.setState({senhaRepeticao: e.target.value})}
                                    />
                                </FormGroup>

                            </div>
                        </div>
                    </div>
                </Card>

            </div>
        );
    }
}