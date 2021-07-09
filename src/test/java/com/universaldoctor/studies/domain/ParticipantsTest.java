package com.universaldoctor.studies.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.universaldoctor.studies.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParticipantsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Participants.class);
        Participants participants1 = new Participants();
        participants1.setId("id1");
        Participants participants2 = new Participants();
        participants2.setId(participants1.getId());
        assertThat(participants1).isEqualTo(participants2);
        participants2.setId("id2");
        assertThat(participants1).isNotEqualTo(participants2);
        participants1.setId(null);
        assertThat(participants1).isNotEqualTo(participants2);
    }
}
