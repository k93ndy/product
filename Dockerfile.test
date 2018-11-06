FROM openjdk:8-alpine as builder
COPY ./ /product
WORKDIR /product
RUN ./gradlew test bootWar

FROM payara/micro:latest
COPY --from=builder /product/build/libs/product-*.war $DEPLOY_DIR