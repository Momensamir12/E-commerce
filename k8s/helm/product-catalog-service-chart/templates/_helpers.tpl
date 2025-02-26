{{/* Define a helper to generate the full MySQL connection URL */}}
{{- define "spring-app.fullMongoDBConnectionURL" -}}
mon:postgresql://postgres:5432/postgres
{{- end -}}