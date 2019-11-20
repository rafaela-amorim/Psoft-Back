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
- v1/api/campanha/find/busca=?{substring}
    retorna uma lista com todas as campanhas que contenham a string como substring no título
- v1/api/campanha/find/descr/busca=?{substring}
    retorna uma lista com todas as campanhas que contenham a string recebida como substring no título OU na descrição    
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
    adiciona um comentario a uma campanha, usuario deve estar autenticado

GET
- v1/api/comentario/campanha/{id}
    retorna a lista com todos os comentarios da campanha correspondente ao id da URI
- v1/api/comentario/respostas/{id}
    retorna a lista de todos os comentarios que referenciam ao comentário correspondente ao id da URI
- v1/api/auth/comentario/usuario
    retorna uma lista com todos os comentarios feitos pelo usuário autenticado que faz a requisição

DELETE
- v1/api/auth/comentario/deletar/{id}
    deleta o comentario correspondente ao id na URI, o usuário que tenta apagar deve ser o mesmo usuário que criou o comentario e o deve estar autenticado. 
    Todos os comentarios resposta deste comentario que esta sendo deletado devem ser deletados em cascata.
    

<h3>Likes</h3>

POST
- v1/api/auth/like
    usuário autenticado dá like em uma campanha

GET
- v1/api/likes/campanha/{url}
    retorna uma lista com todos os likes que foram dados à campanha correspondente à url passado na uri
- v1/api/auth/likes/usuario
    retorna uma lista com todos os likes dados pelo usuário autenticado que fez a requisição

DELETE
- v1/api/auth/like/campanha/remove/{url}
    o usuário dono do like pode retirá-lo de uma campanha correspondente à url na URI, usuário deve estar autenticado 
- v1/api/auth/like/campanha/remove/{id}
    usuário dono do like pode removê-lo através do id da campanha  passado pela URI, usuário deve estar autenticado

<h3>Dislikes</h3>

POST
- v1/api/auth/dislike
    um usuário autenticado pode dar dislike em uma campanha

GET
- v1/api/dislikes/campanha/{url}
    retorna uma lista com todos os dislikes da campanha correspondente à url na URI
- v1/api/auth/dislikes/usuario
    retorna uma lista com todos os likes que foram dados pelo usuário autenticado que fez a requisição

DELETE
- v1/api/auth/dislike/campanha/remove/{url}
    o usuário dono do dislike pode retirá-lo de uma campanha correspondente à url na URI, usuário deve estar autenticado 
- v1/api/auth/dislike/campanha/remove/{id}
    usuário dono do dislike pode removê-lo através do id da campanha passado pela URI, usuário deve estar autenticado