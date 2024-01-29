package com.example.numberninja.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes


val CustomShapes = Shapes(
    small = RoundedCornerShape(
        topStartPercent = 15, topEndPercent = 5,
        bottomEndPercent = 15, bottomStartPercent = 5
    ),

    medium = RoundedCornerShape(
        topStartPercent = 40, topEndPercent = 10,
        bottomEndPercent = 40, bottomStartPercent = 10
    ),

    large = RoundedCornerShape(
        topStartPercent = 100, topEndPercent = 30,
        bottomEndPercent = 100, bottomStartPercent = 30
    ),
)
