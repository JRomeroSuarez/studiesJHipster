import { IStudy } from 'app/shared/model/study.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IParticipants {
  id?: string;
  email?: string | null;
  associatedForms?: string | null;
  formsCompleted?: number | null;
  languaje?: string | null;
  invitationStatus?: Status | null;
  actions?: string | null;
  study?: IStudy | null;
}

export const defaultValue: Readonly<IParticipants> = {};
