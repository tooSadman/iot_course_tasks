package com.kaminovskiy.booking.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kaminovskiy.booking.web.rest.TestUtil;

public class ReceptionistTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Receptionist.class);
        Receptionist receptionist1 = new Receptionist();
        receptionist1.setId(1L);
        Receptionist receptionist2 = new Receptionist();
        receptionist2.setId(receptionist1.getId());
        assertThat(receptionist1).isEqualTo(receptionist2);
        receptionist2.setId(2L);
        assertThat(receptionist1).isNotEqualTo(receptionist2);
        receptionist1.setId(null);
        assertThat(receptionist1).isNotEqualTo(receptionist2);
    }
}
