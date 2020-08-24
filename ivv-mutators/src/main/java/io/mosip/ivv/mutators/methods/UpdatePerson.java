package io.mosip.ivv.mutators.methods;

import io.mosip.ivv.core.base.BaseStep;
import io.mosip.ivv.core.base.StepInterface;
import io.mosip.ivv.core.dtos.IDObjectField;
import io.mosip.ivv.core.dtos.ProofDocument.DOCUMENT_CATEGORY;
import io.mosip.ivv.core.dtos.RequestDataDTO;
import io.mosip.ivv.core.dtos.ResponseDataDTO;
import io.mosip.ivv.core.exceptions.RigInternalError;
import io.mosip.ivv.core.utils.Utils;

import static io.mosip.ivv.core.utils.Utils.regex;

public class UpdatePerson extends BaseStep implements StepInterface {

    @Override
    public void validateStep() throws RigInternalError {
        if(step.getParameters().size() < 1){
            throw new RigInternalError("DSL error: Expect key and its value");
        }

        if(step.getParameters().get(0).isEmpty()){
            throw new RigInternalError("DSL error: key should not be empty");
        }
    }



    @Override
    public void run() {
        String key = step.getParameters().get(0);
        String value = "";
        if(step.getParameters().size()>1){
            value = step.getParameters().get(1);
        }
        if(value == "null"){
            value = null;
        }
        switch(key){
            case "docTypecode":
                store.getCurrentPerson().getProofOfAddress().setDocTypeCode(value);
                break;
            case "docCatCode":
            	store.getCurrentPerson().getProofOfAddress().setDocCatCode(DOCUMENT_CATEGORY.POM);
            	break;
            case "langCode":
                store.getCurrentPerson().setLangCode(value);
                break;
            case "userid":
                store.getCurrentPerson().setUserid(value);
                break;
            case "reqId":
            	store.getCurrentPerson().setReqId(value);
            	break;
            case "version":
            	store.getCurrentPerson().setVersion(value);
            	break;
            case "requesttime":
            	store.getCurrentPerson().setRequesttime(value);
            	break;
            case "otp":
            	store.getCurrentPerson().setOtp(value);
            	break;
            case "date":
                store.getCurrentPerson().getSlot().setDate(value);
                break;
            case "fromTime":
                store.getCurrentPerson().getSlot().setFrom(value);
                break;
            case "toTime":
                store.getCurrentPerson().getSlot().setTo(value);
                break;
            case "registrationId":
                store.getCurrentPerson().setRegistrationId(value);
                break;
            case "registrationCenterId":
            	store.getCurrentPerson().setRegistrationCenterId(value);
            	break;

            case "uin":
                store.getCurrentPerson().setUin(value);
                break;

            case "preRegistrationId":
                store.getCurrentPerson().setPreRegistrationId(value);
                break;

            case "centerId":
                store.getCurrentPerson().setRegistrationCenterId(value);
                break;
            case "docFileFormat":
            	store.getCurrentPerson().getProofOfAddress().setDocFileFormat(value);
            	break;
		/*
		 * case "city": store.getCurrentPerson().setCity(value); break; case
		 * "postalCode": store.getCurrentPerson().setPostalCode(value); break;
		 */

            default:
                if(key==null || key.isEmpty()){
                    this.hasError=true;
                    logSevere("Update Person first parameter cannot be empty ");
                    return;
                }
                if(store.getCurrentPerson().getIdObject().containsKey(key)){
                    IDObjectField idObjectField = store.getCurrentPerson().getIdObject().get(key);
                    if(idObjectField != null){
                        IDObjectField newIdObjectField = Utils.updateIDField(idObjectField, value, store.getCurrentPerson().getPrimaryLang(), store.getCurrentPerson().getSecondaryLang());
                        store.getCurrentPerson().getIdObject().put(key, newIdObjectField);
                    }
                    return;
                }

        }
    }

    @Override
    public RequestDataDTO prepare() {
        return null;
    }

    @Override
    public ResponseDataDTO call(RequestDataDTO requestData) {
        return null;
    }

    @Override
    public void process(ResponseDataDTO res) {

    }
}
