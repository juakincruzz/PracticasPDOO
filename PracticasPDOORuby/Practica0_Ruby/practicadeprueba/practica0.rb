
module Practica0
  class Errores
    def initialize
      @numeros = Array.new(5)
    end
    def Prueba
      for i in 0...@numeros.length
        @numeros[i] = i * 2
      end
      if (@numeros[2] == 4)
        puts "El valor en la posición 2 es igual a 4"
      else
        puts "El valor en la posición 2 NO es 4"
      end
      puts "El último número del array es: " + @numeros.last.to_s
    end
    def Prueba2 (limite)
      #muestra los pares hasta un limite
      i = 0
      while i <= limite
        if i % 2 == 0
        puts i.to_s + " es par"
        else
          puts "#{i} es impar"
        end
        i += 1
      end
      if limite < 0
        puts "El límite es negativo, no se mostrarán números"
      else
        puts "Fin del programa"
      end
    end
  end
end

#simulación del main
puts "Hola mundo"
errores = Practica0::Errores.new
errores.Prueba
errores2 = Practica0::Errores.new
errores2.Prueba2(10)