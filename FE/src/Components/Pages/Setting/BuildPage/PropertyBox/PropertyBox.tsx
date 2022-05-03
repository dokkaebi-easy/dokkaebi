import { useState } from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import DeleteIcon from '@mui/icons-material/Delete';
import Button from '@mui/material/Button';
import SelectItem from 'Components/UI/Atoms/SelectItem/SelectItem';
import TextField from '@mui/material/TextField';
import { BuildProperty } from 'Components/MDClass/BuildData/BuildData';

interface PropertyProps {
  value: BuildProperty;
  buildValue: BuildProperty;
  index: number;
  DelClick: (index: number) => void;
}

const PropertyItem = ['publish', 'volume'];

export default function PropertyBox({
  value,
  buildValue,
  index,
  DelClick,
}: PropertyProps) {
  const [property, setProperty] = useState(value.property);
  const [Data, setData] = useState(value.data);

  const handlePropsPropertyChange = (event: string) => {
    setProperty(event);
    value.property = event;
    buildValue.property = event;
  };
  const handleFirstDataChange = (event: any) => {
    setData(event.target.value);
    value.data = event.target.value;
    buildValue.data = event.target.value;
  };

  const handleDelClick = () => {
    DelClick(index);
  };

  return (
    <Grid container spacing={1}>
      <Grid item xs={2}>
        <Typography>Property</Typography>
        <SelectItem
          defaultValue={property}
          label="Property"
          Items={PropertyItem}
          Change={handlePropsPropertyChange}
        />
      </Grid>
      <Grid item xs={10}>
        <Typography>Data</Typography>
        <TextField
          fullWidth
          label="Data"
          variant="outlined"
          size="small"
          sx={{ my: 1 }}
          placeholder="Data"
          defaultValue={Data}
          onChange={handleFirstDataChange}
        />
      </Grid>
      <Grid item mb={2}>
        <Button
          variant="outlined"
          startIcon={<DeleteIcon />}
          size="small"
          onClick={handleDelClick}
          sx={{
            color: 'red',
            borderColor: 'red',
          }}
        >
          Delete
        </Button>
      </Grid>
    </Grid>
  );
}
