import java.lang.Math.sin
import java.util.*

fun main(args: Array<String>){
    var scan: Scanner = Scanner(System.`in`)
    println("Выберите, к чему будет проведена интерполяция: \n" +
            "1. Таблица из варианта \n" +
            "2. Функция y = x+2 \n" +
            "3. Функция y = x^2-1 \n" +
            "4. Функция sinx")
    var table = mutableMapOf<Double, Double>()
    var ans = scan.nextLine()
    val t = Tables()
    when (ans){
        "1" -> table = t.returnTable().toMutableMap()
        "2" -> {
            println("Введите левую границу промежутка интерполяции")
            val left = scan.nextLine().toDouble()
            println("Введите правую границу промежутка интерполяции")
            val right = scan.nextLine().toDouble()
            var h = (right-left)/7
            var x_prom = 0.0
            var y_prom = 0.0
            for (i in 0..6){
                x_prom = left+(i*h)
                y_prom = x_prom+2
                table[x_prom] = y_prom
            }
        }
        "3" ->{
            println("Введите левую границу промежутка интерполяции")
            val left = scan.nextLine().toDouble()
            println("Введите правую границу промежутка интерполяции")
            val right = scan.nextLine().toDouble()
            var h = (right-left)/7
            var x_prom = 0.0
            var y_prom = 0.0
            for (i in 0..6){
                x_prom = left+(i*h)
                y_prom = (x_prom*x_prom)-1
                table[x_prom] = y_prom
            }
        }
        "4" ->{
            println("Введите левую границу промежутка интерполяции")
            val left = scan.nextLine().toDouble()
            println("Введите правую границу промежутка интерполяции")
            val right = scan.nextLine().toDouble()
            var h = (right-left)/7
            var x_prom = 0.0
            var y_prom = 0.0
            for (i in 0..6){
                x_prom = left+(i*h)
                y_prom = sin(x_prom)
                table[x_prom] = y_prom
            }
        }
    }

    println("Введите, при каком значении Х будем искать значение функции")
    var xLine = scan.nextLine()
    var x = xLine.toDouble()
    val lag = LagrangeInt()
    val New = NewtonInt()
    lag.interpolate(x,table)
    New.interpolate(x, table)
}

