import React from "react";
import Card from "../../components/Card";
import  {withRouter} from'react-router-dom';
import FormGroup from "../../components/form-group";
import SelectMenu from "../../components/SelectMenu";
import LancamentosServices from "../../app/Service/lancamentoService";
import * as messages from "../../components/toastr"
import LocalStorage from "../../app/Service/localStorage";

class CadastroLancamentos extends React.Component{

    state = {
        id: null,
        descricao: '',
        valor: '',
        mes: '',
        ano: '',
        tipo: '',
        usuario: null,
        atualizando: false
    }

    constructor(){
        super();
        this.service = new LancamentosServices();
    }

    componentDidMount(){
        const params = this.props.match.params;
        
        if(params.id){
            this.service
            .obterPorId(params.id)
            .then(response => {
                this.setState({...response.data, atualizando: true})
            }).catch(erros => {
                messages.mensagemErro(erros.response.data)
            })
        }
    }

    submit = () => {

        const usuarioLogado  = LocalStorage.obterItem('_usuario_logado');
        const { descricao, valor,  mes, ano, tipo } = this.state;
        const lancamento = {descricao, valor, mes, ano, tipo, usuario: usuarioLogado.id};

        try{
                this.service.validar(lancamento)
        }catch(erro){
            const mensagem = erro.mensagem;
            mensagem.forEach(msg => messages.mensagemErro(msg));
            return false;
        }
        this.service
        .salvar(lancamento)
        .then(response => {
            this.props.history.push('/consulta-lancamentos')
            messages.mensagemSucesso('Lançamento cadastrado com successo');
        }).catch(error =>{
            messages.mensagemErro(error.reponse.data);
        })
    }

    atualizar = () => {
        const { descricao, valor,  mes, ano, tipo, status, id, usuario } = this.state;
        const lancamento = {descricao, valor,mes, ano, tipo,  status, id, usuario};

        this.service
        .atualizar(lancamento)
        .then(response => {
            this.props.history.push('/consulta-lancamentos')
            messages.mensagemSucesso('Lançamento cadastrado com sucesso');
        }).catch(error =>{
            messages.mensagemErro(error.reponse.data);
        })
    }

    handleChange = (event) => {
        const value = event.target.value;
        const name = event.target.name;
        this.setState({ [name]:  value })
    }

    render(){

        const tipos = this.service.obterListaTipos();
        const meses = this.service.obterMeses();

        return(
            <Card title={ this.state.atualizando ? 'Atualização de Lançamento' : 'Cadastro de Lançamento'  }>
                <div className="row">
                    <div className="col-md-12">
                        <FormGroup id= "inputDesricao" label="Descrição: ">
                            <input  id="inputDescricao" 
                            type="text" 
                            className="form-control"
                            name="descricao"
                            value={this.state.descricao}
                            onChange={this.handleChange}/>
                        </FormGroup>
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-6">
                        <FormGroup id="inputAno" label="Ano: ">
                            <input id="inputAno" 
                            type="text" 
                            className="form-control"
                            name="ano"
                            value={this.state.ano}
                            onChange={this.handleChange}
                            />
                        </FormGroup>
                    </div>
                    <div className="col-md-6">
                    <FormGroup id="inputMes" label="Mes: ">
                            <SelectMenu 
                            id="inputMes" 
                            className="form-control" 
                            lista={meses}
                            name="mes"
                            value={this.state.mes}
                            onChange={this.handleChange}
                            />
                    </FormGroup>
                    </div>
                </div>

                <div className="row">
                    <div className="col-md-4">
                    <FormGroup id="inputValor" label="Valor: ">
                            <input id="inputValor" 
                            type="text"
                             className="form-control"
                             name="valor"
                             value={this.state.valor}
                             onChange={this.handleChange}
                             />
                    </FormGroup>
                    </div>

                    <div className="col-md-4">
                    <FormGroup id="inputTipo" label="Tipo: ">
                            <SelectMenu id="inputTipo" 
                            className="form-control" 
                            lista={tipos} 
                            name="tipo"
                            value={this.state.tipo}
                            onChange={this.handleChange}
                            />
                    </FormGroup>
                    </div>

            

                </div>

                <div className="row">
                    <div className="col-md-6">

                        {
                            this.state.atualizando ?
                            (
                                <button onClick={this.atualizar}
                                 className="btn btn-primary">
                                    <i className="pi pi-refresh"></i> Atualizar
                                </button>        
                            ) : (
                                <button onClick={this.submit} 
                                className="btn btn-success">
                                    <i className="pi pi-save"></i> Salvar
                                    </button>

                            )
                        }
                        <button onClick={e => this.props.history.push('/consulta-lancamentos')} 
                        className="btn btn-danger">
                            <i className="pi pi-times"></i> Cancelar
                            </button>
                    </div>
                </div>
            </Card>
        );
    }
}
export default withRouter(CadastroLancamentos);