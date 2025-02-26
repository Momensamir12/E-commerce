{{/* Define a helper to generate the full MySQL connection URL */}}
{{- define "spring-app.postgresqlURL" -}}
jdbc:postgresql://postgres:5432/postgres
{{- end -}}