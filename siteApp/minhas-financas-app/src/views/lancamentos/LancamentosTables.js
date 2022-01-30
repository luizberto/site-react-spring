import React from "react";
import currencyFormatter from 'currency-formatter'

export default props =>  {

    const rows = props.lancamentos.map(lancamento => {
        return(
            <tr key={lancamento.id}>
                <td>{lancamento.descricao}</td>
                <td>{ currencyFormatter.format(lancamento.valor, {locale: 'pt-BR'})}</td>
                <td>{lancamento.tipo}</td>
                <td>{lancamento.mes}</td>
                <td>{lancamento.status}</td>
                <td>
                <button className="btn btn-success" 
                onClick={e => props.alterarStatus(lancamento, 'EFETIVADO')}
                type="button" title="Efetivar" disabled = {lancamento.status !== 'PENDENTE'}
                >
                   <i className="pi pi-check mr-2"></i>
                </button>

                <button className="btn btn-warning" 
                onClick={e => props.alterarStatus(lancamento, 'CANCELADO')}
                type="button" title="cancelar" disabled = {lancamento.status !== 'PENDENTE'}
                >
                <i className="pi pi-times mr-2"></i>
                </button>

                <button type="button" 
                className="btn btn-primary" 
                onClick={e=> props.editarAction(lancamento.id)} title="editar"><i className="pi pi-pencil mr-2"></i></button>
                <button type="button" 
                className="btn btn-danger" 
                onClick={e => props.deleteAction(lancamento)} title="deletar"><i className="pi pi-trash mr-2"></i></button>
                </td>
            </tr>
        )
    })

    return(

        <table className="table table-hover">
            <thead>
                <tr>
                    <th scope="col">Descrição</th>
                    <th scope="col">valor</th>
                    <th scope="col">Tipo</th>
                    <th scope="col">Mes</th>
                    <th scope="col">Situação</th>
                    <th scope="col">Ações</th>
                </tr>
        </thead>

        <tbody>
            {rows}
        </tbody>

        </table>
    )
}