import React from "react";
import {  withRouter  } from 'react-router-dom';
import Card from "../../components/Card";
import FormGroup from "../../components/form-group";
import SelectMenu from "../../components/SelectMenu";
import LancamentosTables from "./LancamentosTables";
import LancamentosServices from "../../app/Service/lancamentoService";
import LocalStorage from "../../app/Service/localStorage";
import * as messages from "../../components/toastr"
import { Dialog } from 'primereact/dialog';
import { Button } from 'primereact/button';

class ConsultaLancamentos extends React.Component{
    
    state = {
        ano: '',
        mes: '',
        tipo: '',
        showConfirmDialog:false,
        lancamentoDeletar:{},
        descricao:'',
        lancamentos: []
    }

    constructor(){
        super();
        this.service = new LancamentosServices();
    }

    buscar = () => {

        if(!this.state.ano){
            messages.mensagemErro('o preencientodo campo ano é obrigatório')
            return  false;
        }

        const usuarioLogado = LocalStorage.obterItem('_usuario_logado');

        const lancamentoFiltro = {
            ano: this.state.ano,
            mes: this.state.mes,
            tipo: this.state.tipo,
            descricao: this.state.descricao,
            usuario: usuarioLogado.id
        }

        this.service
        .consultar(lancamentoFiltro)
        .then(resposta => {
            this.setState({lancamentos: resposta.data})
        }).catch(error => {
            console.log(error)
        })
    }


    editar = (id) => {
        console.log('editando o  lançamento',id)
    }



    abrirConfirmacao = (lancamento) => {
        this.setState({showConfirmDialog : true, lancamentoDeletar: lancamento})
    }

    cancelarDelecao = ()  => {
        this.setState({showConfirmDialog: false, lancamentoDeletar: {}})
    }

    deletar = () => {
        this.service
        .deletar(this.state.lancamentoDeletar.id)
        .then(response => {
            const lancamentos = this.state.lancamentos;
            const index = lancamentos.indexOf(this.state.lancamentoDeletar)
            lancamentos.splice(index, 1);
            this.setState({lancamentos: lancamentos, showConfirmDialog: false});
            messages.mensagemSucesso('Lançamentodeletado com sucesso')
        }).catch(error=>{
            messages.mensagemErro('Ocorreu um erro ao tentar deletar  o  lançamento')
        })
    }

    

    render(){

        const meses = this.service.obterMeses();  
        const tipos = this.service.obterListaTipos();
        const confirmDialogFooter = (
            <div>
                <div>
                    <Button label="Yes" icon="pi pi-check" onClick={this.deletar} />
                    <Button label="No" icon="pi pi-times" onClick={this.cancelarDelecao} />
                </div>
            </div>
        );

        return(
            <Card title = "Consulta Lançamentos">
                <div className="row">
                    <div className="col-md-6">
                        <div className="bs-component">
                            <FormGroup htmlFor = "inputAno" label = "Ano: ">
                                <input type="text"
                                className="form-control"
                                id="inputAno"
                               value={this.state.ano}
                               onChange={e=> this.setState({ano: e.target.value})}
                                placeholder="Digite o ano"
                                />
                            </FormGroup>

                            <FormGroup htmlFor = "inputMes" label = "Mes: ">
                                <SelectMenu id = "inputMes"
                                value={this.state.mes}
                                onChange={e => this.setState({mes: e.target.value})}
                                className ="form-control" 
                                lista = {meses}/>                          
                            </FormGroup>

                            <FormGroup htmlFor = "inputDesc" label = "Descricao: ">
                                <input type="text" 
                                id = "inpuDesc"
                                value={this.state.descricao}
                                onChange={e => this.setState({descricao: e.target.value})} 
                                className ="form-control" 
                                placeholder="Digite a descrição"
                                />                          
                            </FormGroup>
                            
                            <FormGroup htmlFor = "inputTipo" label = "Tipo: ">
                                <SelectMenu id = "inpuTipo"
                                value={this.state.tipo}
                                onChange={e => this.setState({tipo: e.target.value})} 
                                className ="form-control" 
                                lista = {tipos}/>                          
                            </FormGroup>

                            

                            <button onClick={this.buscar} type="button" className="btn btn-success">Buscar</button>
                            <button type="button" className="btn btn-danger">Cadastrar</button>

                        </div>

                    </div>

                </div>

                <br/>
                <div className="col-md-12">
                    <div className="bs-component">
                        <LancamentosTables lancamentos={this.state.lancamentos}  
                        deleteAction =  {this.abrirConfirmacao}
                        editarAction={this.editar}
                        />
                    </div>
                </div>
                <div>
                <Dialog header="Confiração" 
                visible={this.state.showConfirmDialog} 
                style={{ width: '50vw' }} 
                footer={confirmDialogFooter}
                modal={true} 
                onHide={() => this.setState({showConfirmDialog: false})}>
                    <p>Quer excluis mesmo?</p>
                </Dialog>
                </div>
            </Card>
        );
    }
}
export default ConsultaLancamentos;