package com.baby.babycareproductsshop.validation;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({ValidationGroup.NotNullGroup.class ,ValidationGroup.NotBlankGroup.class,
            ValidationGroup.PatternCheckGroup.class, Default.class})
public interface ValidationSequence {
}
