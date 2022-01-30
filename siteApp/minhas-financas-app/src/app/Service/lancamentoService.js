import ApiService from "../apiService";
import ErroValidacao from "../exception/ErroValidacao";

export default class LancamentosServices extends ApiService{
    constructor(){
        super('/api/lancamentos')
    }


    obterMeses(){
        return[
            {label: 'Selecione', value: ''},
            {label: 'Janeiro', value: 1},
            {label: 'Fevereiro', value: 2},
            {label: 'Março', value: 3},
            {label: 'Abril', value: 4},
            {label: 'Maio', value: 5},
            {label: 'Junho', value: 6},
            {label: 'Julho', value: 7},
            {label: 'Agosto', value: 8},
            {label: 'Setembro', value: 9},
            {label: 'Outubro', value: 10},
            {label: 'Novembro', value: 11},
            {label: 'Dezembro', value: 12}
        ]
    }

    obterListaTipos(){
        return[
            {label: 'Selecione', value:''},
            {label: 'receita', value: 'RECEITA'},
            {label: 'despesa', value: 'DESPESA'}
        ]
    }

    obterPorId(id){
        return this.get(`/${id}`)
    }

    alterarStatus(id, status){
        return this.put(`/${id}/atualiza-status`, {status})
    }

    validar(lancamento){
        const erros = [];

        if(!lancamento.ano){
            erros.push("informe o ano");
        }

        if(!lancamento.mes){
            erros.push("informe o mes");
        }

        if(!lancamento.descricao){
            erros.push("informe uma descrição");
        }

        if(!lancamento.valor){
            erros.push("informe um valor");
        }

        if(!lancamento.tipo){
            erros.push("informe um tipo de lançamento");
        }

        if(erros && erros.length > 0){
            throw new ErroValidacao(erros);
        }
    }

    salvar(lancamento){
        return this.post('/', lancamento);
    }

    atualizar(lancamento){
        return this.put(`/${lancamento.id}`, lancamento);
    }


    consultar(lancamentoFiltro){
        let params = `?ano=${lancamentoFiltro.ano}`

        if(lancamentoFiltro.mes){
            params = `${params}&mes=${lancamentoFiltro.mes}`
        }

        if(lancamentoFiltro.tipo){
            params = `${params}&tipo=${lancamentoFiltro.tipo}`
        }

        if(lancamentoFiltro.status){
            params = `${params}&status=${lancamentoFiltro.status}`
        }

        if(lancamentoFiltro.usuario){
            params = `${params}&usuario=${lancamentoFiltro.usuario}`
        }

        if(lancamentoFiltro.descricao){
            params = `${params}&descricao=${lancamentoFiltro.descricao}`
        }

        return this.get(params)
    }

    deletar(id){
        return this.delete(`/${id}`)
    }
}