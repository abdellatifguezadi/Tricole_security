# Tricol Supply Chain Management System

Application de gestion des approvisionnements et des stocks avec Spring Security et JWT.

## 🚀 Quick Start

### Prérequis
- Java 17+
- Maven 3.6+
- Docker
- MySQL 8.0+ (doit être en cours d'exécution sur localhost)

## 🐳 Docker

### Construction de l'image Docker
```bash
docker build -t tricol-supplierchain:latest .
```

### Exécution du conteneur
Assurez-vous que MySQL est en cours d'exécution sur votre machine locale, puis:

```bash
docker run -d -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/team_tricol_supplier_chain_security?createDatabaseIfNotExist=true \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=votre_password \
  --name tricol-app \
  tricol-supplierchain:latest
```

L'application sera accessible sur `http://localhost:8080`

### Commandes utiles
```bash
# Voir les logs
docker logs -f tricol-app

# Arrêter le conteneur
docker stop tricol-app

# Supprimer le conteneur
docker rm tricol-app

# Redémarrer le conteneur
docker restart tricol-app
```

## 📦 Pousser l'image vers Docker Hub

```bash
# Se connecter à Docker Hub
docker login

# Tag l'image
docker tag tricol-supplierchain:latest votre-username/tricol-supplierchain:latest

# Push vers Docker Hub
docker push votre-username/tricol-supplierchain:latest
```

## 🔧 Configuration GitHub Actions

### Secrets requis
Configurez les secrets suivants dans votre repository GitHub (Settings → Secrets and variables → Actions):

- `DOCKER_USERNAME`: Votre nom d'utilisateur Docker Hub
- `DOCKER_PASSWORD`: Votre token d'accès Docker Hub

### Créer un token Docker Hub
1. Allez sur https://hub.docker.com/settings/security
2. Cliquez sur "New Access Token"
3. Donnez un nom au token et cliquez sur "Generate"
4. Copiez le token et ajoutez-le comme secret `DOCKER_PASSWORD` dans GitHub

## 🏗️ Build local

```bash
# Compiler le projet
mvn clean package

# Exécuter l'application
java -jar target/supplierchain-0.0.1-SNAPSHOT.jar
```

## 📝 API Endpoints

### Authentication
- `POST /api/auth/register` - Inscription
- `POST /api/auth/login` - Connexion
- `POST /api/auth/refresh` - Refresh token

### Statistiques
- `GET /api/statistiques/dashboard` - Statistiques complètes du système (utilisateurs, produits, commandes, stock, finances, alertes)

### Roles
- ADMIN
- RESPONSABLE_ACHATS
- MAGASINIER
- CHEF_ATELIER

## 🔐 Sécurité

L'application utilise:
- Spring Security avec JWT
- Gestion des rôles et permissions
- System d'audit des actions sensibles
- Refresh tokens

## 🛠️ Technologies

- Spring Boot 3.5.7
- Spring Security
- JWT (JSON Web Tokens)
- MySQL 8.0
- Liquibase
- Docker
- GitHub Actions

## 👥 Auteur

Tricol Team
