# ============================================
# Stage 1: Builder - Extração de layers
# ============================================
FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /builder

# Copiar Maven Wrapper e dependências
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Baixar dependências (cache layer)
RUN ./mvnw dependency:go-offline

# Copiar código fonte
COPY src ./src

# Build da aplicação (sem rodar testes)
RUN ./mvnw clean package -DskipTests

# Extrair layers do JAR usando Spring Boot jarmode
RUN java -Djarmode=tools -jar target/*.jar extract --layers --destination extracted

# ============================================
# Stage 2: Runtime - Imagem final otimizada
# ============================================
FROM eclipse-temurin:21-jre-jammy

# Criar usuário não-root para segurança
RUN groupadd -r spring && useradd -r -g spring spring

WORKDIR /application

# Copiar layers extraídas (ordem otimizada para cache)
COPY --from=builder /builder/extracted/dependencies/ ./
COPY --from=builder /builder/extracted/spring-boot-loader/ ./
COPY --from=builder /builder/extracted/snapshot-dependencies/ ./
COPY --from=builder /builder/extracted/application/ ./

# Configurar propriedade do usuário
RUN chown -R spring:spring /application

# Executar como usuário não-root
USER spring

# Expor porta padrão do Spring Boot
EXPOSE 8080

# Iniciar aplicação
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
