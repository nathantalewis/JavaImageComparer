/*
 * Copyright 2001-2009 Artima, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.natotomato.image

/*
ScalaTest also supports the behavior-driven development style, in which you
combine tests with text that specifies the behavior being tested. Here's
an example whose text output when run looks like:

A Map
- should only contain keys and values that were added to it
- should report its size as the number of key/value pairs it contains
*/
import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

class SparseBufferedImageComparatorSpec extends FunSpec with ShouldMatchers {
  val testImageFiles = ("testpng1.png", "testpng2.png", "testpng3.png")
  
  describe("A comparator") {

    it("should match the same image exactly") {
      val image = javax.imageio.ImageIO.read(getClass.getResource("/" + testImageFiles._1))
      val sameImage  = javax.imageio.ImageIO.read(getClass.getResource("/" + testImageFiles._1))
      
      (new SparseBufferedImageComparator(1.0).compare(image, sameImage)) should equal (0)
    }
    
    it("should not match different images exactly") {
      val image = javax.imageio.ImageIO.read(getClass.getResource("/" + testImageFiles._1))
      val otherImage = javax.imageio.ImageIO.read(getClass.getResource("/" + testImageFiles._3))
      
      (new SparseBufferedImageComparator(1.0).compare(image, otherImage)) should not equal (0)
    }
    
    it("should match all images when zero accuracy is desired") {
      val image = javax.imageio.ImageIO.read(getClass.getResource("/" + testImageFiles._1))
      val otherImage = javax.imageio.ImageIO.read(getClass.getResource("/" + testImageFiles._3))
      
      (new SparseBufferedImageComparator(0.0).compare(image, otherImage)) should equal (0)
    }
    
    it("should not match similar images exactly") {
      val image = javax.imageio.ImageIO.read(getClass.getResource("/" + testImageFiles._1))
      val similarImage = javax.imageio.ImageIO.read(getClass.getResource("/" + testImageFiles._2))
      
      (new SparseBufferedImageComparator(1.0).compare(image, similarImage)) should not equal (0)
    }
    
    it("should match similar images approximately") {
      val image = javax.imageio.ImageIO.read(getClass.getResource("/" + testImageFiles._1))
      val similarImage = javax.imageio.ImageIO.read(getClass.getResource("/" + testImageFiles._2))
      
      (new SparseBufferedImageComparator(0.90).compare(image, similarImage)) should equal (0)
    }
  }
}
