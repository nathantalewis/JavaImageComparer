package com.natotomato.image

/**
 * @author Nathan T.A. Lewis
 */

import java.awt.image.BufferedImage
import java.util.Comparator

class SparseBufferedImageComparator(final val accuracy: Double) extends Comparator[BufferedImage] {
  def compare(image1: BufferedImage, image2: BufferedImage): Int = {
    val rgbPoints = for(i <- 0 until 9;
        j <- 0 until 9)
          yield 
        (image1.getRGB(((j + 0.5) * image1.getWidth()/9.0).toInt, ((i + 0.5) * image1.getHeight()/9.0).toInt),
        image2.getRGB(((j + 0.5) * image2.getWidth()/9.0).toInt, ((i + 0.5) * image2.getHeight()/9.0).toInt))
    
    def pixelAbsoluteValue(p: Int): Int = { (0 until 4).map(i => (p >> i*8) & 0xFF).sum }
    
    val pixelValueTotal = rgbPoints.map(pTuple => pixelAbsoluteValue(pTuple._1) - pixelAbsoluteValue(pTuple._2))
    val realizedAccuracy = pixelValueTotal.count(_==0).toDouble / pixelValueTotal.count(p => true)
    
    val pixelValueSum = pixelValueTotal.sum
    return if (realizedAccuracy >= accuracy) 0 else pixelValueSum
  }

}
