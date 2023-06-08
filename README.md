# Giovanna Sousa RM94767
# Henry Kinoshita RM93443
# Luiz Felipe Souza de Oliveira RM94538
# Matheus Felipe RM93772
# Victor Mendes RM92843

## Diagrama de Classes
![DIAGRAMA](Digital%20Business%20Enablement/diagramaclasses.png)

## Modelo relacional
![MODELO](DataBase%20Application%20%26%20Data%20Science/Relational_1.png)

## Vídeo Pitch
https://youtu.be/V5JjRuASgcI

## Instalação & Execução

Para a instalação é necessário ter o Docker instalado.
Na pasta Digital Business Enablement (essa pasta), execute o comando:

``
docker-compose up -d --build
``

Ele fará a instalação dos containers no docker e executar automaticamente.

Faça Requisições na API com o URL: `localhost:8080/cropsage/api`

## Endpoints
- Usuário
  - [Cadastrar Usuário](#cadastrar-usuário)
  - [Fazer Login](#fazer-login)
  - [Ver Usuário](#ver-usuário)
  - [Editar Usuário](#editar-usuário)
  - [Deletar Usuário](#deletar-usuário)
- Solo
  - [Cadastrar Solo](#cadastrar-solo)
  - [Ver Solo](#ver-solo)
  - [Ver todos os Solos](#ver-todos-os-solos)
  - [Ver Solos por produto](#ver-solos-por-produto)
  - [Ver Solos por localização](#ver-solos-por-localização)

## USUÁRIO

### Cadastrar Usuário

`POST` /usuario/cadastrar

*Campos de requisição*

| campo | tipo   | obrigatório | descrição        |
|-------|--------|:-----------:|------------------|
| nome  | String |     sim     | Nome do usuário  |
| email | String |     sim     | Email do usuário |
| senha | String |     sim     | Senha do usuário |

*Exemplo de requisição*
```
{
	"nome":"nome",
	"email":"email@gmail.com",
	"senha":"senha"
}
```

*Resposta*

| código | descrição                            |
|--------|--------------------------------------|
| 201    | o usuário foi cadastrado com sucesso |
| 403    | dados inválidos                      |

*Exemplo de resposta*
```
{
	"id": 1,
	"nome": "nome",
	"email": "email@gmail.com",
	"senha": "$2a$10$muvCQRFLLWZBlmQqWLRVgO4m2JDGD3q/.1D1z.xn.SJLV3EjkIMHO",
	"enabled": true,
	"accountNonExpired": true,
	"credentialsNonExpired": true,
	"accountNonLocked": true,
	"authorities": [
		{
			"authority": "ROLE_USUARIO"
		}
	],
	"password": "$2a$10$muvCQRFLLWZBlmQqWLRVgO4m2JDGD3q/.1D1z.xn.SJLV3EjkIMHO",
	"username": "email@gmail.com"
}
```

### Fazer Login

`POST` /usuario/login

*Campos de requisição*

| campo | tipo   | obrigatório | descrição        |
|-------|--------|:-----------:|------------------|
| email | String |     sim     | Email do usuário |
| senha | String |     sim     | Senha do usuário |

*Exemplo de requisição*
```
{
	"email":"email@gmail.com",
	"senha":"senha"
}
```

*Resposta*

| código | descrição                        |
|--------|----------------------------------|
| 200    | o usuário foi logado com sucesso |
| 403    | dados incorretos                 |

Essa requisição retorna um Token que é necessário para fazer qualquer outra requisição além dessa e do cadastro.

*Exemplo de resposta*
```
{
	"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJlbWFpbEBnbWFpbC5jb20iLCJpc3MiOiJDcm9wU2FnZSIsImV4cCI6MTY4NjEwMzc1M30.lru4fN3gDOkXxPmwDptPI4ubfy3b0BPWUF5j-Z2qTRY",
	"type": "JWT",
	"prefix": "Bearer"
}
```

### Ver Usuário

`GET` /usuario

`HEADER:` Authorization
`VALUE:` Bearer {TOKEN}

*Resposta*

| código | descrição                         |
|--------|-----------------------------------|
| 200    | o usuário foi exibido com sucesso |
| 404    | o usuário não foi encontrado      |

*Exemplo de resposta*
```
{
	"id": 1,
	"nome": "nome",
	"email": "email@gmail.com",
	"senha": "$2a$10$muvCQRFLLWZBlmQqWLRVgO4m2JDGD3q/.1D1z.xn.SJLV3EjkIMHO",
	"enabled": true,
	"accountNonExpired": true,
	"credentialsNonExpired": true,
	"accountNonLocked": true,
	"authorities": [
		{
			"authority": "ROLE_USUARIO"
		}
	],
	"password": "$2a$10$muvCQRFLLWZBlmQqWLRVgO4m2JDGD3q/.1D1z.xn.SJLV3EjkIMHO",
	"username": "email@gmail.com"
}
```

### Editar Usuário

`PUT` /usuario

`HEADER:` Authorization
`VALUE:` Bearer {TOKEN}

*Campos de requisição*

| campo | tipo   | obrigatório | descrição        |
|-------|--------|:-----------:|------------------|
| nome  | String |     sim     | Nome do usuário  |
| email | String |     sim     | Email do usuário |
| senha | String |     sim     | Senha do usuário |

*Exemplo de requisição*
```
{
	"nome":"nomeATUALIZADO",
	"email":"emailATUALIZADO@gmail.com",
	"senha":"senhaATUALIZADA"
}
```

*Resposta*

| código | descrição                            |
|--------|--------------------------------------|
| 200    | o usuário foi atualizado com sucesso |
| 404    | o usuário não foi encontrado         |

*Exemplo de resposta*
```
{
	"id": 1,
	"nome": "nomeATUALIZADO",
	"email": "emailATUALIZADO@gmail.com",
	"senha": "$2a$10$S7M4u0HNvMXWX0VnMO5irO7DxkZ9GCRE/I.gFRwCNiZR4nkK2jDDm",
	"enabled": true,
	"accountNonExpired": true,
	"credentialsNonExpired": true,
	"accountNonLocked": true,
	"authorities": [
		{
			"authority": "ROLE_USUARIO"
		}
	],
	"password": "$2a$10$S7M4u0HNvMXWX0VnMO5irO7DxkZ9GCRE/I.gFRwCNiZR4nkK2jDDm",
	"username": "emailATUALIZADO@gmail.com"
}
```

### Deletar Usuário

`DELETE` /usuario

`HEADER:` Authorization
`VALUE:` Bearer {TOKEN}

*Resposta*

| código | descrição                          |
|--------|------------------------------------|
| 204    | o usuário foi deletado com sucesso |
| 404    | o usuário não foi encontrado       |

## SOLO

### Cadastrar solo

`POST` /solo

`HEADER:` Authorization
`VALUE:` Bearer {TOKEN}

*Campos de requisição*

| campo       | tipo        | obrigatório | descrição                             |
|-------------|-------------|:-----------:|---------------------------------------|
| nitrogenio  | double      |     sim     | Porcentagem de Nitrogênio no solo     |
| potassio    | double      |     sim     | Porcentagem de Potássio no solo       |
| fosforo     | double      |     sim     | Porcentagem de Fósforo no solo        |
| temperatura | double      |     sim     | Temperatura ambiente média em °C      |
| umidade     | double      |     sim     | Porcentagem de umidade no solo        |
| ph          | double      |     sim     | PH presente no solo                   |
| chuva       | double      |     sim     | Quantidade de chuva em mm             |
| localizacao | Localizacao |     sim     | Objeto que representa o local do solo |
| latitude    | String      |     sim     | Latitude geográfica da localização    |
| longitude   | String      |     sim     | Longitude geográfica da localização   |
| nome        | String      |     sim     | Nome dado a localização               |
| cep         | String      |     não     | CEP da localização                    |

*Exemplo de requisição*
```
{
	"nitrogenio": 40,
	"potassio": 80,
	"fosforo": 55,
	"temperatura": 20,
	"umidade": 70,
	"ph": 4,
	"chuva": 200,
	"localizacao": {
		"latitude":"-23.57394734355841",
		"longitude":" -46.62323813354157",
		"nome":"Sítio do Jorge",
    "cep":"01538-001"
	}
}
```

*Resposta*

| código | descrição                         |
|--------|-----------------------------------|
| 200    | o solo foi cadastrado com sucesso |
| 403    | dados inválidos                   |

*Exemplo de resposta*
```
{
	"id": 1,
	"nitrogenio": 40.0,
	"potassio": 80.0,
	"fosforo": 55.0,
	"temperatura": 20.0,
	"umidade": 70.0,
	"ph": 4.0,
	"chuva": 200.0,
	"usuario": {
		"id": 1,
		"nome": "nome",
		"email": "email@gmail.com",
		"senha": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
		"enabled": true,
		"accountNonExpired": true,
		"credentialsNonExpired": true,
		"accountNonLocked": true,
		"authorities": [
			{
				"authority": "ROLE_USUARIO"
			}
		],
		"password": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
		"username": "email@gmail.com"
	},
	"produto": {
		"id": 1,
		"nome": "mamão",
		"epoca": "qualquer época do ano"
	},
	"localizacao": {
		"id": 1,
		"longitude": " -46.62323813354157",
		"latitude": "-23.57394734355841",
		"nome": "Sítio do Jorge",
		"cep": "01538-001",
		"usuario": {
			"id": 1,
			"nome": "nome",
			"email": "email@gmail.com",
			"senha": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
			"enabled": true,
			"accountNonExpired": true,
			"credentialsNonExpired": true,
			"accountNonLocked": true,
			"authorities": [
				{
					"authority": "ROLE_USUARIO"
				}
			],
			"password": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
			"username": "email@gmail.com"
		}
	}
}
```

### Ver solo

`GET` /solo/{id}

`HEADER:` Authorization
`VALUE:` Bearer {TOKEN}

*Resposta*

| código | descrição                      |
|--------|--------------------------------|
| 200    | o solo foi exibido com sucesso |
| 404    | o solo não foi encontrado      |
| 403    | o solo não é do usuário        |

*Exemplo de resposta*
```
{
	"id": 1,
	"nitrogenio": 40.0,
	"potassio": 80.0,
	"fosforo": 55.0,
	"temperatura": 20.0,
	"umidade": 70.0,
	"ph": 4.0,
	"chuva": 200.0,
	"usuario": {
		"id": 1,
		"nome": "nome",
		"email": "email@gmail.com",
		"senha": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
		"enabled": true,
		"accountNonExpired": true,
		"credentialsNonExpired": true,
		"accountNonLocked": true,
		"authorities": [
			{
				"authority": "ROLE_USUARIO"
			}
		],
		"password": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
		"username": "email@gmail.com"
	},
	"produto": {
		"id": 1,
		"nome": "mamão",
		"epoca": "qualquer época do ano"
	},
	"localizacao": {
		"id": 1,
		"longitude": " -46.62323813354157",
		"latitude": "-23.57394734355841",
		"nome": "Sítio do Jorge",
		"cep": "01538-001",
		"usuario": {
			"id": 1,
			"nome": "nome",
			"email": "email@gmail.com",
			"senha": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
			"enabled": true,
			"accountNonExpired": true,
			"credentialsNonExpired": true,
			"accountNonLocked": true,
			"authorities": [
				{
					"authority": "ROLE_USUARIO"
				}
			],
			"password": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
			"username": "email@gmail.com"
		}
	}
}
```

### Ver todos os solos

`GET` /solo/{id}

`HEADER:` Authorization
`VALUE:` Bearer {TOKEN}

*Resposta*

| código | descrição                          |
|--------|------------------------------------|
| 200    | os solos foram exibido com sucesso |
| 404    | usuário não foi encontrado         |

*Exemplo de resposta*
```
{
	"content": [
		{
			"id": 1,
			"nitrogenio": 40.0,
			"potassio": 80.0,
			"fosforo": 55.0,
			"temperatura": 20.0,
			"umidade": 70.0,
			"ph": 4.0,
			"chuva": 200.0,
			"usuario": {
				"id": 1,
				"nome": "nome",
				"email": "email@gmail.com",
				"senha": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
				"enabled": true,
				"accountNonExpired": true,
				"credentialsNonExpired": true,
				"accountNonLocked": true,
				"authorities": [
					{
						"authority": "ROLE_USUARIO"
					}
				],
				"password": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
				"username": "email@gmail.com"
			},
			"produto": {
				"id": 1,
				"nome": "mamão",
				"epoca": "qualquer época do ano"
			},
			"localizacao": {
				"id": 1,
				"longitude": " -46.62323813354157",
				"latitude": "-23.57394734355841",
				"nome": "Sítio do Jorge",
				"cep": "01538-001",
				"usuario": {
					"id": 1,
					"nome": "nome",
					"email": "email@gmail.com",
					"senha": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
					"enabled": true,
					"accountNonExpired": true,
					"credentialsNonExpired": true,
					"accountNonLocked": true,
					"authorities": [
						{
							"authority": "ROLE_USUARIO"
						}
					],
					"password": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
					"username": "email@gmail.com"
				}
			}
		},
		{
			"id": 2,
			"nitrogenio": 70.0,
			"potassio": 50.0,
			"fosforo": 65.0,
			"temperatura": 27.0,
			"umidade": 50.0,
			"ph": 6.0,
			"chuva": 120.0,
			"usuario": {
				"id": 1,
				"nome": "nome",
				"email": "email@gmail.com",
				"senha": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
				"enabled": true,
				"accountNonExpired": true,
				"credentialsNonExpired": true,
				"accountNonLocked": true,
				"authorities": [
					{
						"authority": "ROLE_USUARIO"
					}
				],
				"password": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
				"username": "email@gmail.com"
			},
			"produto": {
				"id": 2,
				"nome": "banana",
				"epoca": "outubro a março"
			},
			"localizacao": {
				"id": 2,
				"longitude": " -46.62323813354157",
				"latitude": "-23.57394734355841",
				"nome": "Sítio do João",
				"cep": "01538-001",
				"usuario": {
					"id": 1,
					"nome": "nome",
					"email": "email@gmail.com",
					"senha": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
					"enabled": true,
					"accountNonExpired": true,
					"credentialsNonExpired": true,
					"accountNonLocked": true,
					"authorities": [
						{
							"authority": "ROLE_USUARIO"
						}
					],
					"password": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
					"username": "email@gmail.com"
				}
			}
		}
	],
	"pageable": {
		"sort": {
			"empty": true,
			"sorted": false,
			"unsorted": true
		},
		"offset": 0,
		"pageNumber": 0,
		"pageSize": 5,
		"paged": true,
		"unpaged": false
	},
	"totalPages": 1,
	"totalElements": 2,
	"last": true,
	"size": 5,
	"number": 0,
	"sort": {
		"empty": true,
		"sorted": false,
		"unsorted": true
	},
	"numberOfElements": 2,
	"first": true,
	"empty": false
}
```

### Ver solos por localização

`GET` /solo/localizacao/{nome}

`HEADER:` Authorization
`VALUE:` Bearer {TOKEN}

*Resposta*

| código | descrição                          |
|--------|------------------------------------|
| 200    | os solos foram exibido com sucesso |
| 404    | usuário não foi encontrado         |

*Exemplo de resposta*
```
{
	"content": [
		{
			"id": 2,
			"nitrogenio": 70.0,
			"potassio": 50.0,
			"fosforo": 65.0,
			"temperatura": 27.0,
			"umidade": 50.0,
			"ph": 6.0,
			"chuva": 120.0,
			"usuario": {
				"id": 1,
				"nome": "nome",
				"email": "email@gmail.com",
				"senha": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
				"enabled": true,
				"accountNonExpired": true,
				"credentialsNonExpired": true,
				"accountNonLocked": true,
				"authorities": [
					{
						"authority": "ROLE_USUARIO"
					}
				],
				"password": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
				"username": "email@gmail.com"
			},
			"produto": {
				"id": 2,
				"nome": "banana",
				"epoca": "outubro a março"
			},
			"localizacao": {
				"id": 2,
				"longitude": " -46.62323813354157",
				"latitude": "-23.57394734355841",
				"nome": "Sítio do João",
				"cep": "01538-001",
				"usuario": {
					"id": 1,
					"nome": "nome",
					"email": "email@gmail.com",
					"senha": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
					"enabled": true,
					"accountNonExpired": true,
					"credentialsNonExpired": true,
					"accountNonLocked": true,
					"authorities": [
						{
							"authority": "ROLE_USUARIO"
						}
					],
					"password": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
					"username": "email@gmail.com"
				}
			}
		}
	],
	"pageable": {
		"sort": {
			"empty": true,
			"sorted": false,
			"unsorted": true
		},
		"offset": 0,
		"pageNumber": 0,
		"pageSize": 5,
		"paged": true,
		"unpaged": false
	},
	"totalPages": 1,
	"totalElements": 1,
	"last": true,
	"size": 5,
	"number": 0,
	"sort": {
		"empty": true,
		"sorted": false,
		"unsorted": true
	},
	"numberOfElements": 1,
	"first": true,
	"empty": false
}
```

### Ver solos por produto

`GET` /solo/produto/{nome}

`HEADER:` Authorization
`VALUE:` Bearer {TOKEN}

*Resposta*

| código | descrição                          |
|--------|------------------------------------|
| 200    | os solos foram exibido com sucesso |
| 404    | usuário não foi encontrado         |

*Exemplo de resposta*
```
{
	"content": [
		{
			"id": 2,
			"nitrogenio": 70.0,
			"potassio": 50.0,
			"fosforo": 65.0,
			"temperatura": 27.0,
			"umidade": 50.0,
			"ph": 6.0,
			"chuva": 120.0,
			"usuario": {
				"id": 1,
				"nome": "nome",
				"email": "email@gmail.com",
				"senha": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
				"enabled": true,
				"accountNonExpired": true,
				"credentialsNonExpired": true,
				"accountNonLocked": true,
				"authorities": [
					{
						"authority": "ROLE_USUARIO"
					}
				],
				"password": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
				"username": "email@gmail.com"
			},
			"produto": {
				"id": 2,
				"nome": "banana",
				"epoca": "outubro a março"
			},
			"localizacao": {
				"id": 2,
				"longitude": " -46.62323813354157",
				"latitude": "-23.57394734355841",
				"nome": "Sítio do João",
				"cep": "01538-001",
				"usuario": {
					"id": 1,
					"nome": "nome",
					"email": "email@gmail.com",
					"senha": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
					"enabled": true,
					"accountNonExpired": true,
					"credentialsNonExpired": true,
					"accountNonLocked": true,
					"authorities": [
						{
							"authority": "ROLE_USUARIO"
						}
					],
					"password": "$2a$10$kj4bCO6L7gE7VGu46Ffb/uPrGYv065EBDOJRRJrsxlcAG16v.86u.",
					"username": "email@gmail.com"
				}
			}
		}
	],
	"pageable": {
		"sort": {
			"empty": true,
			"sorted": false,
			"unsorted": true
		},
		"offset": 0,
		"pageNumber": 0,
		"pageSize": 5,
		"paged": true,
		"unpaged": false
	},
	"totalPages": 1,
	"totalElements": 1,
	"last": true,
	"size": 5,
	"number": 0,
	"sort": {
		"empty": true,
		"sorted": false,
		"unsorted": true
	},
	"numberOfElements": 1,
	"first": true,
	"empty": false
}
```
