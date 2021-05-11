class LagrangeInt {
    fun interpolate(x: Double, table: Map<Double,Double>): Double{
        var res = 0.0
        var chisl = 1.0
        var znam = 1.0
        var x0 = 0.0
        table.forEach{
            x0 = it.key
            chisl = 1.0
            znam = 1.0
            table.forEach{ new ->
                if (new!=it) {
                    chisl *= (x - new.key)
                    znam *= (x0 - new.key)
                }
            }
            res+=it.value*(chisl/znam)
        }
        println("результат по Лагранжу: $res")
        return res
    }
}
