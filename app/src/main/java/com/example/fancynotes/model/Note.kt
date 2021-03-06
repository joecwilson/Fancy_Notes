/*
 * Copyright (c) 2022 Joseph Wilson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.example.fancynotes.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A data class representing information inside of a standard note. Includes information about
 * the title of the note, the body of the note, and the Notes position. Use null to have the primary
 * key autogenerated.
 */
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    @NonNull @ColumnInfo(name = "note_title")
    var title: String,
    @NonNull @ColumnInfo(name = "note_body")
    var body: String,
    @NonNull @ColumnInfo(name = "note_position")
    var position: Int
)

/**
 * Returns the first 500 characters of the [Note]. Then finishes out the word by grabbing the first
 * space, comma, question mark, period, exclamation mark, or backslash after the 300 character
 * cutoff, and adds a ... to tell user that there is more to the note that has been cut off, and is
 * visible if you select the note as a whole.
 */
fun Note.getTruncatedBody(): String {
    val maxLength = 500
    return if (body.length > maxLength) {
        var truncated: String = body.substring(0, maxLength)
        val left: String = body.substring(maxLength)
        truncated += left.split(Regex("[\\s,?.!\\\\]"))[0]
        truncated += " ..."
        truncated
    } else {
        body
    }
}


/**
 * Because room changes the null to a value because autogenerate is turned on, this lets you test
 * if two notes have the same content excluding the ID.
 */
fun Note.equalsIgnoreID(note: Note): Boolean {
    if ((body == note.body) && (title == note.title) && (position == note.position)) {
        return true
    }
    return false
}