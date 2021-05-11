import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.xy.XYDataset
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import java.awt.FlowLayout
import java.lang.System.exit
import javax.swing.JFrame
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import java.awt.BasicStroke
import java.awt.Color


class NewtonInt {
    fun interpolate(x: Double, table: Map<Double,Double>){
        val newTable = table.toSortedMap()
        val mid = (newTable.lastKey()+newTable.firstKey())/2
        val xs = table.keys.toList()
        val y = table.values.toList()
        var delta1 = mutableListOf<Double>()
        var delta2 = mutableListOf<Double>()
        var delta3 = mutableListOf<Double>()
        var delta4 = mutableListOf<Double>()
        var delta5 = mutableListOf<Double>()
        var delta6 = mutableListOf<Double>()
        for (i in 1..6){
            delta1.add(y[i]-y[i-1])
            if(i>=2){
                delta2.add(delta1[i-1]-delta1[i-2])
            }
            if(i>=3){
                delta3.add(delta2[i-2]-delta2[i-3])
            }
            if(i>=4){
                delta4.add(delta3[i-3]-delta3[i-4])
            }
            if(i>=5){
                delta5.add(delta4[i-4]-delta4[i-5])
            }
            if(i==6){
                delta6.add(delta5[i-5]-delta5[i-6])
            }
        }

        var res = 0.0
        if(x<=mid){
            println("Искомый Х находится в первой половине отрезка")
            var t = (x-xs[0])/(xs[6]-xs[5])
            res = y[0]+t*delta1[0]+((t*(t-1)*delta2[0])/2)+((t*(t-1)*(t-2)*delta3[0])/6)+((t*(t-1)*(t-2)*(t-3)*delta4[0])/24)+((t*(t-1)*(t-2)*(t-3)*(t-4)*delta5[0])/120)+((t*(t-1)*(t-2)*(t-3)*(t-4)*(t-5)*delta6[0])/720)
        }
        else{
            var t = (x-xs[6])/(xs[6]-xs[5])
            println("Искомый Х находится во второй половине отрезка")
            res = y[6]+t*delta1[5]+((t*(t+1)*delta2[4])/2)+((t*(t+1)*(t+2)*delta3[3])/6)+((t*(t+1)*(t+2)*(t+3)*delta4[2])/24)+((t*(t+1)*(t+2)*(t+3)*(t+4)*delta5[1])/120)+((t*(t+1)*(t+2)*(t+3)*(t+4)*(t+5)*delta6[0])/720)
        }
        println("результат по Ньютону: $res")
        val series = XYSeries("Your fun")
        val series2 = XYSeries("Points")
        val series3 = XYSeries("Your point")
        val series4 = XYSeries("Newton")
        table.forEach{
            series.add(it.key, it.value)
            series2.add(it.key, it.value)
        }
        val h = xs[6]-xs[5]
        val leftNewton = xs[0]+(h/2)
        var x_New = 0.0
        var y_New = 0.0
        for (i in 0..5){
            x_New = leftNewton + i*h
            if(x_New<=mid){
                var t = (x_New-xs[0])/(xs[6]-xs[5])
                y_New = y[0]+t*delta1[0]+((t*(t-1)*delta2[0])/2)+((t*(t-1)*(t-2)*delta3[0])/6)+((t*(t-1)*(t-2)*(t-3)*delta4[0])/24)+((t*(t-1)*(t-2)*(t-3)*(t-4)*delta5[0])/120)+((t*(t-1)*(t-2)*(t-3)*(t-4)*(t-5)*delta6[0])/720)
            }
            else{
                var t = (x_New-xs[6])/(xs[6]-xs[5])
                y_New = y[6]+t*delta1[5]+((t*(t+1)*delta2[4])/2)+((t*(t+1)*(t+2)*delta3[3])/6)+((t*(t+1)*(t+2)*(t+3)*delta4[2])/24)+((t*(t+1)*(t+2)*(t+3)*(t+4)*delta5[1])/120)+((t*(t+1)*(t+2)*(t+3)*(t+4)*(t+5)*delta6[0])/720)
            }
            series4.add(x_New, y_New)
        }
        series3.add(x,res)
        val col = XYSeriesCollection(series)
        col.addSeries(series2)
        col.addSeries(series3)
        col.addSeries(series4)
        val xyDataset: XYDataset = col
        val chart = ChartFactory .createXYLineChart("Your fun", "x", "y",
            xyDataset,
            PlotOrientation.VERTICAL,
            true, true, true)
        val frame = JFrame("График ответа")
        frame.layout = FlowLayout()
        // Помещаем график на фрейм
        frame.contentPane.add(ChartPanel(chart))
        val renderer = XYLineAndShapeRenderer()
        val plot = chart.xyPlot
        renderer.setSeriesShapesVisible(0, false)
        renderer.setSeriesLinesVisible (1, false)
        renderer.setSeriesShapesVisible(1, true)
        renderer.setSeriesLinesVisible (2, false)
        renderer.setSeriesShapesVisible(2, true)
        renderer.setSeriesPaint  (0, Color.BLUE);
        renderer.setSeriesPaint  (1, Color.RED);
        renderer.setSeriesPaint  (2, Color.GREEN);
        renderer.setSeriesPaint  (3, Color.YELLOW);
        renderer.setSeriesStroke (2, BasicStroke(4f));
        frame.setSize(800, 600)
        plot.renderer=renderer
        frame.pack()
        frame.isVisible=true
    }
    fun checkNewton(table: Map<Double,Double>){
        var newTable = table.toSortedMap()
        var diff = 0.0
        var last = 0.0
        var counter = 0
        newTable.forEach{
            if(counter!=0){
                if(counter!=1){
                    if(it.key-last==diff) {
                        diff = it.key - last
                        last = it.key
                    }
                    else{
                        println("Узлы не равноостоящие, интерполяция по Ньютону невозможна")
                        exit(0)
                    }
                }
                else{
                    diff = it.key-last
                    last = it.key
                }
                counter+=1
            }
            else{
                counter+=1
                last = it.key
            }

        }
    }
}

