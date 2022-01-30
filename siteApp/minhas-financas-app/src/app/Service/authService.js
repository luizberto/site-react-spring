import LocalStorage from "./localStorage";

export default class AuthService{
    static isUsuarioAutenticado(){
        const usuario = LocalStorage.obterItem('_usuario_logado')
        return usuario && usuario.id;
    }

    static removerUsuarioAutenticado(){
        localStorage.removeItem('_usuario_logado')
    }

    static logar(usuario){
        LocalStorage.adcionarItem('_usuario_logado', usuario)
    }

    static obterUsuarioAutenticado(){
        return LocalStorage.obterItem('_usuario_logado')
    }
}