## Controle de Sonda Espacial

Essa aplicação consiste em uma solução para o controle de sondas espaciais capazes
de se movimentar por um planeta.

### Passo a passo para a execução da aplicação:

1. Instalação do Docker:

2. Instalar o docker seguindo o tutorial para o seu sistema operacional: https://docs.docker.com/engine/install/

### Configuração do banco de dados:

1. Para subir uma instância do banco de dados em sua máquina execute o comando docker-compose up -d a partir do diretório: .../space-probe-challeng.

2. Acesse o banco através de um driver da sua preferência utilizando as credenciais que se encontram no arquivo docker-compose.yml.

3. Crie um banco de dados de nome space-probe-control

### Execução da aplicação:

1. Com o banco de dados configurado execute os comandos gradle clean build e gradle bootRun no serviço.

2. Após o serviço estar rodando, teremos acesso aos recursos documentados em http://localhost:8080/swagger-ui.html

### Considerações finais:

- Tentei otimizar a configuração inicial da aplicação utilizando scripts que rodariam junto com o docker para a criação
da tabela necessária, porém não obtive sucesso.
- Li o artigo sobre a utilização dos getters e setters, entendi a forma como deveria ser feito, porém tive um pouco de
dificuldade em utilizá-los da forma como foi solicitado, talvez por conta dos hábitos do projeto atual.
