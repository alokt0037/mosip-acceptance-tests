package io.mosip.ivv.registration.methods;

import io.mosip.ivv.core.base.Step;
import io.mosip.ivv.core.base.StepInterface;
import io.mosip.registration.dto.ErrorResponseDTO;
import io.mosip.registration.dto.ResponseDTO;
import io.mosip.registration.dto.SuccessResponseDTO;
import io.mosip.registration.exception.RegBaseCheckedException;
import io.mosip.registration.service.sync.PolicySyncService;

public class SyncPolicy extends Step implements StepInterface {

    @Override
    public void run() {
        PolicySyncService pss = store.getRegApplicationContext().getBean(PolicySyncService.class);
        ResponseDTO responseDTO = null;
        try {
            responseDTO = pss.fetchPolicy();
        } catch (RegBaseCheckedException e) {
            e.printStackTrace();
            logSevere(e.getMessage());
            this.hasError = true;
            return;
        }
        if(responseDTO.getErrorResponseDTOs() != null && responseDTO.getErrorResponseDTOs().size() > 0){
            for(ErrorResponseDTO es: responseDTO.getErrorResponseDTOs()){
                logInfo("Message: "+es.getMessage()+", code: "+es.getCode()+", infoType: "+es.getInfoType());
            }
            this.hasError = true;
            return;
        }else{
            SuccessResponseDTO es = responseDTO.getSuccessResponseDTO();
            logInfo("Message: "+es.getMessage()+", code: "+es.getCode()+", infoType: "+es.getInfoType());
        }
    }
}