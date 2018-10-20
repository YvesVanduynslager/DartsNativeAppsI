/**
 * Copyright (c) 2017 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.tile.yvesv.nativeappsiproject

import android.databinding.BindingAdapter
import android.widget.ImageView

/**
 * A binding adapter allows us to perform actions on an element which are not supported by default data binding.
 * In this case we are storing a resource integer for the image to be displayed,
 * but data binding does not provide a default way to display an image from an ID.
 * To fix that, you have a BindingAdapter that takes a reference to the object that it was invoked from,
 * along with a parameter.
 * It uses that to call setImageResource on the imageView that displays the image for the comic.
 *
 * See: recycler_item_player.xml
 */
object DataBindingAdapters
{
    @BindingAdapter("android:src")
    fun setImageResource(imageView: ImageView, resource: Int)
    {
        imageView.setImageResource(resource)
    }
}
