<h1>Rotas</h1>

<h3>Usuario</h3>

POST
- v1/api/usuarios
    adiciona um usuário novo, depois de verificar que o email ja nao existia no sistema

GET
- v1/api/usuarios
    retorna uma lista com todos os usuarios
- v1/api/usuari/{email}
    retorna o usuario do email na URI, caso exista.

PUT
- v1/api/usuarios/{email}
    muda a senha do usuario do email na URI, caso exista, e retorna o usuario

<h3>Login</h3>

POST
- v1/api/login/usuarios
    faz login de um usuario (autentica), caso o usuario exista

<h3>Campanha</h3>

POST
- v1/api/auth/campanha 
    adiciona uma nova campanha ao sistema

GET
- v1/api/campanha/{url}
    retorna a campanha correspondente à url na URI, caso a campanha exista
- v1/api/campanha/find
    retorna uma lista com todas as campanhas que contenham a substring do paramentro
- v1/api/campanha/status/{url}
    retorna o status atual da campanha correspondente à url na URI

PUT
- v1/api/auth/campanha/encerrar/{url}
    o usuario que for dono da campanha corresponde à url na URI pode encerrá-la
- v1/api/auth/campanha/deadline/{url}
    o usuario que for dono da campanha corresponde à url na URI pode mudar a data que ela vence
- v1/api/auth/campanha/meta/{url}
    o usuario que for dono da campanha corresponde à url na URI pode alterar a meta de doações
- v1/api/auth/campanha/descricao/{url}
    o usuario que for dono da campanha corresponde à url na URI pode alterar a descrição

<h3>Comentario</h3>

POST
- v1/api/auth/comentario
    adiciona um comentario a uma campanha
    

<h3>Likes</h3>

<h3>Dislikes</h3>