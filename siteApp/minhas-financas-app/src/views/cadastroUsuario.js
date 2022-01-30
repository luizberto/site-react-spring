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

    

    cadastrar = () => {

       const {nome, email, senha, senhaRepeticao} = this.state;

      const usuario = {
          nome,
          email,
          senha,
          senhaRepeticao
      }

      try{
        this.service.validar(usuario)
      }catch(erro){
        const msgs = erro.mensagem
        msgs.forEach(msg => mensagemErro(msg));
        return false;
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
                                
                                <button  onClick={this.cadastrar}
                                 type="button" 
                                 className="btn btn-success">
                                    <i  className="pi pi-save"></i> salvar
                                </button>
                                <button onClick={this.cancelar} 
                                type="button" 
                                className="btn btn-danger">
                                   <i className="pi pi-times"></i> sair
                                </button>
                            </div>
                        </div>
                    </div>
                </Card>
            
            </>
        );
    }
}

export default withRouter (CadastroUsuario);

