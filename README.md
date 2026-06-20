[![MiniShop CI Pipeline](https://github.com/Henrycori/minishop/actions/workflows/ci.yml/badge.svg)](https://github.com/Henrycori/minishop/actions/workflows/ci.yml)

# MiniShop - Proyecto de Construcción y Pruebas de Software

Este proyecto es una aplicación desarrollada con **Spring Boot** para la gestión de productos de una tienda tecnológica, utilizada como base para la implementación de pipelines de automatización de pruebas y aseguramiento de la calidad.

## 🚀 Integración Continua (CI)
El proyecto cuenta con un flujo de trabajo automatizado mediante **GitHub Actions** configurado en `.github/workflows/ci.yml`.

### Etapas del Pipeline:
1. **Checkout:** Clonación del código fuente en el entorno virtual.
2. **Setup Java:** Configuración del entorno de ejecución con Java 17 (Temurin) y caché de dependencias con Maven.
3. **Compilation:** Compilación estática de los módulos principales (`mvn compile`).
4. **Unit Tests:** Ejecución de pruebas unitarias aisladas con JUnit 5 y Mockito (`mvn test`).
5. **Integration Tests:** Validación de persistencia e integración de componentes (`mvn verify`).
6. **Artifacts Publication:** Almacenamiento y publicación de los reportes XML/HTML generados por Surefire.

---
*Tecsup - IV Ciclo - Laboratorio N° 13*
