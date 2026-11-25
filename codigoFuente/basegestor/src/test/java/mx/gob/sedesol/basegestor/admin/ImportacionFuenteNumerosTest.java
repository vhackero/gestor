package mx.gob.sedesol.basegestor.admin;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.*;
import javax.persistence.*;
import mx.gob.sedesol.basegestor.model.repositories.admin.UsuariosImportarRepo;

public class ImportacionFuenteNumerosTest {
    @Test public void relacionAceptaTiposNumericosDeHibernate() {
        UsuariosImportarRepo repo=new UsuariosImportarRepo();
        repo.entityManager=mock(EntityManager.class);Query consulta=mock(Query.class);
        when(repo.entityManager.createNativeQuery(anyString())).thenReturn(consulta);
        for(Number tipo:Arrays.<Number>asList(1,1L,java.math.BigInteger.ONE,java.math.BigDecimal.ONE,(short)1)) {
            when(consulta.getResultList()).thenReturn(Collections.singletonList(tipo));
            assertTrue(repo.verificarRelacionConvocatoria("3","17"));
        }
        when(consulta.getResultList()).thenReturn(Collections.singletonList(0));
        assertFalse(repo.verificarRelacionConvocatoria("3","17"));
        when(consulta.getResultList()).thenReturn(Collections.emptyList());
        assertFalse(repo.verificarRelacionConvocatoria("3","17"));
    }
}
