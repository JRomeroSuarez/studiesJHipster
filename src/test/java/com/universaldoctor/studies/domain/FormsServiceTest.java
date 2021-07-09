package com.universaldoctor.studies.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.universaldoctor.studies.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FormsServiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Forms.class);
        Forms forms1 = new Forms();
        forms1.setId("id1");
        Forms forms2 = new Forms();
        forms2.setId(forms1.getId());
        assertThat(forms1).isEqualTo(forms2);
        forms2.setId("id2");
        assertThat(forms1).isNotEqualTo(forms2);
        forms1.setId(null);
        assertThat(forms1).isNotEqualTo(forms2);
    }
}
