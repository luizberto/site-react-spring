import React from "react";
import Card from "../components/Card";
import FormGroup from "../components/form-group";
import Navbar from "../components/navbar";
import { withRouter } from "react-router-dom";
import UsuarioService from "../app/Service/usuarioService";
import { mensagemErro, mensagemSucesso} from "../components/toastr";




class CadastroUsuario extends React.Component {

    state = {
        nome: '',
        email: '',
        senha: '',
        senhaRepeticao:''
    }

    constructor(){
        super();
        this.service = new UsuarioService();
    }

    validar(){
        const msgs = [];

        if(!this.state.nome){
            msgs.push('O campo Nome é obrigatório')
        }

        if(!this.state.email){
            msgs.push('O campo email é obrogatório')
        }else if(!this.state.email.match(/^[a-z0-9.]+@[a-z0-9]+\.[a-z]/)){
            msgs.push('Informe um email valido')
        }

        if(!this.state.senha || this.state.senhaRepeticao){
            msgs.push('Digite a senha 2x')
        }else if(this.state.senha !== this.state.senhaRepeticao){
            msgs.push('As senhas não batem')
        }

        return msgs;
    }

    cadastrar = () => {

        const msgs = this.validar();

        if(msgs && msgs.length > 0){
            msgs.forEach((msg, index) => {
                mensagemErro(msg);
            })

            return false;
        }

      const usuario = {
          nome: this.state.nome,
          email: this.state.email,
          senha: this.state.senha
      }

      this.service.salvar(usuario)
      .then( reponse => {
          mensagemSucesso('Usuario cadastrado com sucesso')
          this.props.history.push('/login')
      }).catch( error => {
          mensagemErro(error.response.data)
      })
    }

   

    cancelar = () => {
        this.props.history.push('/login')
    }

    render(){
        return(
            <>
            <Navbar/>
                <Card title="Cadastro de usuario">
                    <div className = "row">
                        <div className = "col-lg-12">
                            <div className = "bs-component">
                                <FormGroup label = "Nome: " htmlFor = "inputNome">
                                    <input type = "text"
                                    id="inputNome"
                                    className="form-control"
                                    name = "nome"
                                    onChange = {e => this.setState({nome: e.target.value})}
                                    />
                                </FormGroup>

                                <FormGroup label = "Email: " htmlFor = "inputEmail">
                                    <input type = "email"
                                    id="inputEmail"
                                    className="form-control"
                                    name = "email"
                                    onChange = {e => this.setState({email: e.target.value})}
                                    />
                                </FormGroup>

                                <FormGroup label = "Senha: " htmlFor = "inputSenha">
                                    <input type = "password"
                                    id="inputSenha"
                                    className="form-control"
                                    name = "senha"
                                    onChange = {e => this.setState({senha: e.target.value})}
                                    />
                                </FormGroup>

                                <FormGroup label = "Repita a senha: " htmlFor = "inputRepitaSenha">
                                    <input type = "password"
                                    id="inputRepitaSenha"
                                    className="form-control"
                                    name = "senha"
                                    onChange = {e => this.setState({senhaRepeticao: e.target.value})}
                                    />
                                </FormGroup>
                                
                                <button  onClick={this.cadastrar} type="button" className="btn btn-success">salvar</button>
                                <button onClick={this.cancelar} type="button" className="btn btn-danger">sair</button>
                            </div>
                        </div>
                    </div>
                </Card>
            
            </>
        );
    }
}

export default withRouter (CadastroUsuario);

