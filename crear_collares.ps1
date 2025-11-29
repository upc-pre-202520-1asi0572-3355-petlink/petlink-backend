# Script para crear 3 collares en la base de datos, usen .\crear_collares.ps1 en la consola :b

$baseUrl = "http://localhost:8080/api/collare"

# Collar 1
$collar1 = @{
    codigo = "COL-001"
    modelo = "SmartCollar Pro"
    descripcion = "Collar inteligente con sensor de ritmo cardiaco"
} | ConvertTo-Json

Write-Host "Creando Collar 1..." -ForegroundColor Cyan
try {
    $response1 = Invoke-RestMethod -Uri $baseUrl -Method Post -Body $collar1 -ContentType "application/json"
    Write-Host "Collar 1 creado exitosamente:" -ForegroundColor Green
    $response1 | ConvertTo-Json
} catch {
    Write-Host "Error al crear Collar 1: $_" -ForegroundColor Red
}

# Collar 2
$collar2 = @{
    codigo = "COL-002"
    modelo = "SmartCollar Lite"
    descripcion = "Collar basico con GPS y monitoreo de actividad"
} | ConvertTo-Json

Write-Host "`nCreando Collar 2..." -ForegroundColor Cyan
try {
    $response2 = Invoke-RestMethod -Uri $baseUrl -Method Post -Body $collar2 -ContentType "application/json"
    Write-Host "Collar 2 creado exitosamente:" -ForegroundColor Green
    $response2 | ConvertTo-Json
} catch {
    Write-Host "Error al crear Collar 2: $_" -ForegroundColor Red
}

# Collar 3
$collar3 = @{
    codigo = "COL-003"
    modelo = "SmartCollar Max"
    descripcion = "Collar premium con monitoreo completo de signos vitales"
} | ConvertTo-Json

Write-Host "`nCreando Collar 3..." -ForegroundColor Cyan
try {
    $response3 = Invoke-RestMethod -Uri $baseUrl -Method Post -Body $collar3 -ContentType "application/json"
    Write-Host "Collar 3 creado exitosamente:" -ForegroundColor Green
    $response3 | ConvertTo-Json
} catch {
    Write-Host "Error al crear Collar 3: $_" -ForegroundColor Red
}

Write-Host "`n=== Resumen ===" -ForegroundColor Yellow
Write-Host "Proceso completado. Verifica los collares creados." -ForegroundColor Yellow
