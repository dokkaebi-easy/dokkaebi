import React, { useEffect, useState } from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import DeleteIcon from '@mui/icons-material/Delete';
import Button from '@mui/material/Button';
import SelectItem from 'Components/UI/Atoms/SelectItem/SelectItem';
import TextField from '@mui/material/TextField';
import { Property } from 'Components/MDClass/PropertyData/PropertyData';

interface PropertyProps {
  value: Property;
  buildValue: Property;
  index: number;
  DelClick: (index: number) => void;
}

const PropertyItem = ['publish', 'volume', 'evn'];

export default function PropertyBox({
  value,
  buildValue,
  index,
  DelClick,
}: PropertyProps) {
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

  const handleDelClick = () => {
    DelClick(index);
  };

  return (
    <Grid container spacing={2}>
      <Grid item>
        <Typography>Property</Typography>
        <SelectItem
          defaultValue={value.property}
          label="Property"
          Items={PropertyItem}
          Change={handlePropsPropertyChange}
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
        <Button
          variant="outlined"
          startIcon={<DeleteIcon />}
          size="small"
          onClick={handleDelClick}
          sx={{ color: 'black', borderColor: 'black' }}
        >
          Delete
        </Button>
      </Grid>
    </Grid>
  );
}
