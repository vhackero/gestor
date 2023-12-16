package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.math.BigInteger;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import mx.gob.sedesol.basegestor.model.entities.admin.TblOrganismoGubernamental;

@Generated(value="Dali", date="2016-12-27T10:28:10.821-0600")
@StaticMetamodel(TblPlan.class)
public class TblPlan_ {
	public static volatile SingularAttribute<TblPlan, Integer> idPlan;
	public static volatile SingularAttribute<TblPlan, Date> fechaActualizacion;
	public static volatile SingularAttribute<TblPlan, Date> fechaInicio;
	public static volatile SingularAttribute<TblPlan, Date> fechaRegistro;
	public static volatile SingularAttribute<TblPlan, Date> fechaTermino;
	public static volatile SingularAttribute<TblPlan, String> fundamentacion;
	public static volatile SingularAttribute<TblPlan, String> identificador;
	public static volatile SingularAttribute<TblPlan, String> nombre;
	public static volatile SingularAttribute<TblPlan, String> objetivos;
	public static volatile SingularAttribute<TblPlan, String> perfilEgreso;
	public static volatile SingularAttribute<TblPlan, String> perfilIngreso;
	public static volatile SingularAttribute<TblPlan, Byte> ponderacionCalif;
	public static volatile SingularAttribute<TblPlan, BigInteger> usuarioRegistro;
	public static volatile ListAttribute<TblPlan, RelPlanAptitud> relPlanAptitudes;
	public static volatile ListAttribute<TblPlan, RelPlanConocimiento> relPlanConocimientos;
	public static volatile SingularAttribute<TblPlan, CatCompetenciasPlan> catCompetenciasPlan;
	public static volatile SingularAttribute<TblPlan, CatDocumentosExpidePlan> catDocumentosExpidePlan;
	public static volatile SingularAttribute<TblPlan, CatEstatusPlan> catEstatusPlan;
	public static volatile SingularAttribute<TblPlan, CatModalidadPlanPrograma> catModalidadPlanPrograma;
	public static volatile SingularAttribute<TblPlan, CatNivelEnsenanzaPrograma> catNivelEnsenanzaPrograma;
	public static volatile SingularAttribute<TblPlan, TblOrganismoGubernamental> tblOrganismoGubernamental;
	public static volatile SingularAttribute<TblPlan, CatTipoPlan> catTipoPlan;
	public static volatile SingularAttribute<TblPlan, CatPeriodo> catPeriodo;
	public static volatile SingularAttribute<TblPlan, CatAlcancePlan> catAlcancePlan;
	public static volatile ListAttribute<TblPlan, RelPlanHabilidad> relPlanHabilidades;

	public static volatile SingularAttribute<TblPlan, CatCreditosPlan> catCreditosPlan;
	public static volatile SingularAttribute<TblPlan, CatDivisionesPlan> catDivisionesPlan;
}
