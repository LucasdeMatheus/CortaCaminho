<h1 align="center"> PontoCom </h1>

# Índice
* [Índice](#índice)
* [Descrição do Projeto](#descrição-do-projeto)
* [Status do Projeto](#status-do-projeto)
* [Funcionalidades](#funcionalidades)
* [Instalação do aplicativo](#Instalação-do-aplicativo)
* [Tecnologias utilizadas](#tecnologias-utilizadas)

# Descrição do Projeto

<p>CortaCaminho é um Sistema de Encurtar url,
<br>Alem de encurtar urls, esse projeto possui funcionalidades atrativas, como pesquisar por palavras chaves e um sistema de status para fins de bloqueio.</p>

# Status do Projeto
<img loading="lazy" src="http://img.shields.io/static/v1?label=STATUS&message=CONCLUIDO&labelColor=%2339362C&color=%2328a745&style=for-the-badge" />


# Funcionalidades
![image](https://github.com/user-attachments/assets/33027e16-a8c4-43a0-b15f-87da62f87aa9)<bR>
![image](https://github.com/user-attachments/assets/3f9326d6-14ee-4e8f-a11e-62875e843a8b)


### Request Login POST: (/login)
   >>esta request fará a autenticação de um usuario com Login e Senha e irá gerar um token, liberando acesso de todo o sistema.<br>
   ![image](https://github.com/user-attachments/assets/3e561db3-4ac5-4f87-90ac-f107c745c75a)




### Request Register: (/register)
>>esta request, recebendo um json, cadastra usuarios no banco de dados.
<br>o json deve ser construido nesse formato:<br>
![image](https://github.com/user-attachments/assets/bbceddc8-9a94-4175-b937-39e6616f4d20)


### Request Shorten and Save: (/{idUser}/urls/save)
>>esta request salva url e encurta-as.

### Request Delete Url: (/{idUser}/urls/delete)
>>esta request recebe um json com uma lista de id's para apagar.<br>
![image](https://github.com/user-attachments/assets/5aba1d81-4031-4155-bfa2-f18670b15ddf)



### Request Edit Url: (/{idUser}/urls/edit/{url_id})
>>esta request recebe um json com dados de atualização dde urls, podendo editar img, url e a url encurtada.
![image](https://github.com/user-attachments/assets/bb510213-de92-49f7-a4a5-0d703d468e79)

### Request Update/To schedule Status: (/{idUser}/urls/update-status)
>>esta request recebe um json que atualiza o status da url para que possa ser desativada ou ativada,
>><br>e programada para que seja desativada/ativada.<br>
![image](https://github.com/user-attachments/assets/a04c7307-3e0c-4d38-abcd-b86cb887d3ad)


## Request que não necessitam Autenticação:

### Request Get Profile: (/redirect/profile/{id_profile})
>>esta request recebe um id como parametro, que retornará dados de um perfil.
![image](https://github.com/user-attachments/assets/faccbb92-3711-46ec-aa92-ad74846b5807)



### Request Get Url (/redirect/url/{urlId})
>>esta request recebe um id como parametro, que retornará dados de uma url.
![image](https://github.com/user-attachments/assets/4281389c-8817-46f7-a0be-ff2e896d6d39)
>><br>
>> esta request tambem adiciona uma view, um sistema para dar relevancia nas urls.



### Request Get Url and User by Keyword (/search/{keyword}/{filter})
>>esta request retorna um Page, recebendo uma palvra chave e um filtro, com dados buscados por Login(que seria o @ unico), ou palavra-chave, ou titulo da url.
>><br>
>> Filtros disponivel:<br>
>>![image](https://github.com/user-attachments/assets/9d98953b-03c2-4fff-9f59-77af21edec75)


# Tecnologias utilizadas

<p>linguagem de programação: JDK v21</p>
<p>Framework: Spring</p>
<p>IDE: Intellij IDEA</p>
<p>framework Open Source: Insomnia</p>
<p>Banco de dados: MySQL</p>


