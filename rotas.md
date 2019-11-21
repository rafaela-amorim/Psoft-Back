<h1>Rotas</h1>

<h3>Usuario</h3>

POST
- v1/api/usuarios
    adiciona um usuário novo, depois de verificar que o email ja nao existia no sistema
    
    body da requisição:
    ```json
    {
    	"nome":"nome-do-usuario",
    	"ultimoNome":"ultimo-nome-do-usuario",
    	"email":"email-do-usuario",
    	"cartaoDeCredito":"cartao-de-credito",
    	"senha":"senha-do-usuario"
    }
    ```
    
    retorna
    
    - 200, se o usuario for criado
    
    - 409, se ja existir um usuario com aquele email

GET
- v1/api/usuarios
    retorna uma lista com todos os usuarios
    - 200, se ocorrer tudo bem
    
- v1/api/usuarios/{email}
    retorna o usuario do email na URI, caso exista.
    
    - 200, se o usuario existir
    
    - 404, se o usuario nao existir
    
- v1/api/usuarios/campanhas/{email}
	retorna a lista de campanhas que o usuario é dono
	
	- 200, se ocorrer tudo bem
	
	- 400, se o usuario não existir

PUT
- v1/api/usuarios/{email}
    muda a senha do usuario do email na URI, caso exista, e retorna o usuario
    
    body da requisição:
    ```json
    {
    	"senha":"nova-senha-do-usuario"
    }
    ```
    
    retorna
    
    - 200, se ocorer tudo bem
    
    - 404, se o usuario não existir
    
<h3>Login</h3>

POST
- v1/api/login/usuarios
    faz login de um usuario (autentica), caso o usuario exista
    
    body da requisição:
    ```json
    {
    	"email":"email-do-usuario",
    	"senha":"senha-do-usuario"
    }
    ```
    
    retorna
    
    - 200, se o usuario conseguir se logar e o json com o token
    
     ```json
    {
    	"token":"token-retornado-pelo-servidor"
    }
    ```
    - 401, se a senha for inválida
    
    - 404, se o email não existir
    
<h3>Campanha</h3>

POST
- v1/api/auth/campanha 
    adiciona uma nova campanha ao sistema
    
    body da requisição:
    ```json
    {
    	"nome":"nome-da-campanha",
    	"descricao":"descricao-da-campanha",
    	"dataLimite":"YYYY-MM-DD",
    	"meta": meta-da-campanha
    }
    ```
    
    retorna 
    
    - 201, se a campanha for cadastrada com sucesso
    
    - 404, se houver algum erro

GET
- v1/api/campanha/{url}
    retorna a campanha correspondente à url na URI, caso a campanha exista
    
    retorna 
    
    - 200, se a campanha existir
    
    - 400, se a campanha não existir
    
- v1/api/campanha/find/busca=?{substring}
    retorna uma lista com todas as campanhas que contenham a string como substring no título
    
    retorna
    
    - 200, se ocorrer tudo bem
    
- v1/api/campanha/find/descr/busca=?{substring}
    retorna uma lista com todas as campanhas que contenham a string recebida como substring no título OU na descrição
    
    return 
    
    200, se ocorrer tudo bem
        
- auth/campanha/doacao
	retorna todas as campanhas que um usuario doou
	
	retorna
	
	- 200, se o usuario existir
	
	- 400, se o usuario não existir
	
    

PUT
- v1/api/auth/campanha/encerrar/{url}
    o usuario que for dono da campanha corresponde à url na URI pode encerrá-la
    
    retorna
    
    - 200, se ocorrer tudo bem
    
    - 400, se a campanha não existir
    
- v1/api/auth/campanha/deadline/{url}
    o usuario que for dono da campanha corresponde à url na URI pode mudar a data que ela vence
    
    body da requisição:
    ```json
    {
    	"data":"YYYY-MM-DD"
    }
    ```
    
    retorna
    
    - 200, se ocorrer tudo bem
    
    - 400, se houver algum erro
    
    
    
- v1/api/auth/campanha/meta/{url}
    o usuario que for dono da campanha corresponde à url na URI pode alterar a meta de doações
    
    body da requisição:
    ```json
    {
    	"meta": nova-meta-da-campanha
    }
    ```
    
    retorna
    
    - 200, se ocorrer tudo bem
    
    - 400, se houver algum erro
    
- v1/api/auth/campanha/descricao/{url}
    o usuario que for dono da campanha corresponde à url na URI pode alterar a descrição
    
    body da requisição:
    ```json
    {
    	"descricao": "nova-descricao-da-campanha"
    }
    ```
    
    retorna
    
    - 200, se ocorrer tudo bem
    
    - 412, se a campanha não estiver ativa
    
    - 403, se o usuario não for dono
    
    - 404, se a campanha não existir 

<h3>Comentario</h3>

POST
- v1/api/auth/comentario
    adiciona um comentario a uma campanha, usuario deve estar autenticado
    
    body da requisição, caso seja um comentario a uma campanha:
    ```json
    {
    	"comentario":"comentario-do-usuario",
    	"idCampanha": id-da-campanha-a-ser-adicionado-o-comentario
    }
    ```
    
    body da requisição, caso seja uma resposta a um comentario:
   	```json
   	{
   		"comentario":"comentario-do-usuario"
   		"idComentario": id-do-comentario-a-ser-respondido
   	}
   	```
   	
   	retorna
   	
   	- 200, se o comentario for adicionado
   	
   	- 404, se a campanha ou o comentario não exitir

GET
- v1/api/comentario/campanha/{url}
    retorna a lista com todos os comentarios da campanha correspondente ao id da URI
    
    retorna
    
    - 200, se ocorrer tudo certo
    
    - 404, se a campanha não existir
    
- v1/api/comentario/respostas/{id}
    retorna a lista de todos os comentarios que referenciam ao comentário correspondente ao id da URI
    
    retorna
    
    - 200, se correu tudo bem
    
    - 400, se o comentario não existe
    
- v1/api/auth/comentario/usuario
    retorna uma lista com todos os comentarios feitos pelo usuário autenticado que faz a requisição
    
    retorna
    
    - 200, se tudo correu bem
    
    - 400, se o usuario não foi encontrado
    

DELETE
- v1/api/auth/comentario/deletar/{id}
    deleta o comentario correspondente ao id na URI, o usuário que tenta apagar deve ser o mesmo usuário que criou o comentario e o deve estar autenticado. 
    Todos os comentarios resposta deste comentario que esta sendo deletado devem ser deletados em cascata.
    
    retorna
    
    - 200, se tudo correu bem
    
    - 403, se usuario não é o dono
    
    - 404, se algo deu errado
    

<h3>Likes</h3>

POST
- v1/api/auth/like
    usuário autenticado dá like em uma campanha
    
    body da requisição:
    ```json
    {
    	"email":"email-de-quem-faz-a-doacao",
    	"urlCampanha":"url-da-campanha-a-fazer-a-doacao"
    }
    ```
    
    retorna
    
    - 201, se ocorreu tudo bem
    
    - 409, se esse usuario ja deu like
    
    - 404, se não existe usuario ou campanha

GET
- v1/api/likes/campanha/{url}
    retorna uma lista com todos os likes que foram dados à campanha correspondente à url passado na uri
    
    retorna
    
    - 200, se tudo correu bem
    
    - 400, se não foi encontrada uma campanha com aquela url
    
- v1/api/auth/likes/usuario
    retorna uma lista com todos os likes dados pelo usuário autenticado que fez a requisição
    
    retorna
    
    - 200, se tudo correu bem
    
    - 400, se o usuario não existe

DELETE
- v1/api/auth/like/campanha/remove/{url}
    o usuário dono do like pode retirá-lo de uma campanha correspondente à url na URI, usuário deve estar autenticado 
    
    retorna
    
    - 200, se tudo correu bem
    
    - 400, se a url da campanha ou o usuario não existe


<h3>Dislikes</h3>

POST
- v1/api/auth/dislike
    um usuário autenticado pode dar dislike em uma campanha
    
	body da requisição:
	```json
	{
		"email":"email-do-usuario-que-deseja-dar-dislike",
		"urlCampanha":"url-da-campanha"
	}
	```
	
	retorna
	
	- 201, se correu tudo bem
	
	- 409, se o usuario já deu dislike
	
	- 404, se o usuario ou a campanha não existe

GET
- v1/api/dislikes/campanha/{url}
    retorna uma lista com todos os dislikes da campanha correspondente à url na URI
    
    retorna
    
    - 200, se correu tudo bem
    
    - 400, se a campanha não existe
    
- v1/api/auth/dislikes/usuario
    retorna uma lista com todos os likes que foram dados pelo usuário autenticado que fez a requisição
    
    retorna
    
    - 200, se correu tudo bem
    
    - 400, se o usuario não existe

DELETE
- v1/api/auth/dislike/campanha/remove/{url}
    o usuário dono do dislike pode retirá-lo de uma campanha correspondente à url na URI, usuário deve estar autenticado 
    
    retorna
    
    - 200, se correu tudo bem
    
    - 400, se a campanha não existe
    
    
<h3>Doacao</h3>

POST
- v1/api/auth/doacao
	adiciona uma doação a uma capanha, o usuario tem que estar autenticado para realizar a doação
	
	body da requisição:
	```json
	{
		"emailDoador":"email-de-quem-doou",
		"quantida":quantida-doada,
		"urlCampanha":"url-da-campanha-que-vai-receber"
	}
	```
	
	retorna
	
	- 200, se tudo correu bem
	
	- 400, se o usuario ou a campanha não existe
	
	
GET
	
- v1/api/doacoes/campanha/{url}
	retorna todas as doações realizadas a uma campanha
	
	retorna
	
	- 200, se tudo correu bem
	
	- 400, se a campanha não foi encontrada
	
- v1/api/auth/doacoes/usuario
	retorna todas as doações feitas por um usuario
	
	retorna
	
	- 200, se tudo correu bem
	
	- 400, se o usuario não existe
