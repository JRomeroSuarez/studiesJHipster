import { IForms } from 'app/shared/model/forms.model';
import { IParticipants } from 'app/shared/model/participants.model';

export interface IStudy {
  id?: string;
  title?: string | null;
  description?: string | null;
  numParticipants?: number | null;
  numForms?: number | null;
  forms?: IForms[] | null;
  participants?: IParticipants[] | null;
}

export const defaultValue: Readonly<IStudy> = {};
