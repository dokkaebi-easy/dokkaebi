import React, { useEffect, useState } from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import DeleteIcon from '@mui/icons-material/Delete';
import Button from '@mui/material/Button';
import SelectItem from 'Components/UI/Atoms/SelectItem/SelectItem';
import TextField from '@mui/material/TextField';
import PropertyData, {
  Property,
} from 'Components/MDClass/PropertyData/PropertyData';
import BuildData, { Build } from 'Components/MDClass/BuildData/BuildData';

interface PropertyProps {
  value: Property;
  buildValue: Property;
}

const PropertyItem = ['Publish', 'Volume', 'Evn', 'Build'];

export default function PropertyBox({ value, buildValue }: PropertyProps) {
  const [property, setProperty] = useState(value.property);
  const [firstData, setFirstData] = useState(value.first);
  const [secondData, setSecondData] = useState(value.second);

  const handlePropsPropertyChange = (event: string) => {
    setProperty(event);
    value.property = event;
    buildValue.property = event;
  };
  const handleFirstDataChange = (event: any) => {
    setFirstData(event.target.value);
    value.first = event.target.value;
    buildValue.first = event.target.value;
  };
  const handleSecondDataChange = (event: any) => {
    setSecondData(event.target.value);
    value.second = event.target.value;
    buildValue.second = event.target.value;
  };

  return (
    <Grid container spacing={2}>
      <Grid item>
        <Typography>Property</Typography>
        <SelectItem
          defaultValue={value.property}
          label="Property"
          Items={PropertyItem}
          change={handlePropsPropertyChange}
        />
      </Grid>
      <Grid item>
        <Typography>Data1</Typography>
        <TextField
          id="outlined-basic"
          label="Data1"
          variant="outlined"
          size="small"
          sx={{ my: 1 }}
          placeholder="Data1"
          defaultValue={firstData}
          onChange={handleFirstDataChange}
        />
      </Grid>
      <Grid item>
        <Typography>Data2</Typography>
        <TextField
          id="outlined-basic"
          label="Data2"
          variant="outlined"
          size="small"
          sx={{ my: 1 }}
          placeholder="Data2"
          defaultValue={secondData}
          onChange={handleSecondDataChange}
        />
      </Grid>
      <Grid item sx={{ marginBottom: 1, marginTop: 'auto' }}>
        <Button variant="outlined" startIcon={<DeleteIcon />} size="small">
          Delete
        </Button>
      </Grid>
    </Grid>
  );
}
